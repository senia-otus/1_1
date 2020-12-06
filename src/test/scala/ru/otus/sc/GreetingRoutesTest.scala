package ru.otus.sc

import cats.effect.IO
import ru.otus.sc.greet.GreetingClient
import ru.otus.sc.greet.dao.{GreetingDao, UserDao}
import ru.otus.sc.greet.model.GreetingMethod.{BotGreetingMethod, GuestGreetingMethod, UserGreetingMethod}
import ru.otus.sc.greet.model.{Bot, Guest, User}
import org.http4s.circe.CirceEntityCodec.circeEntityDecoder
import org.http4s.circe.jsonEncoder

class GreetingRoutesTest extends Test {
  "Greeting routes" should {
    "create user" in { (userDao: UserDao, client: GreetingClient) =>
      for {
        user        <- client.createUser(User(None, None, "user name"))
        fetchedUser <- IO(userDao.find(user.id.get))
      } yield {
        fetchedUser shouldBe a[Some[_]]
        user shouldBe fetchedUser.get
        user.name shouldBe "user name"
      }
    }

    "greet user" in { (greetingDao: GreetingDao, client: GreetingClient) =>
      for {
        user             <- client.createUser(User(None, None, "user name"))
        greeting         <- client.greetUser(user.id.get)
        fetchedGreetings <- IO(greetingDao.findGreetings(user.id, None))
      } yield {
        fetchedGreetings.size shouldBe 1
        greeting shouldBe fetchedGreetings.head
        greeting.greetingMethod shouldBe a[UserGreetingMethod.type]
        greeting.text shouldBe "Hi, user name !"
      }
    }

    "greet subordinates" in { (client: GreetingClient) =>
      for {
        ceo       <- client.createUser(User(None, None, "ceo"))
        tco       <- client.createUser(User(None, ceo.id, "tco"))
        _         <- client.createUser(User(None, tco.id, "engineer"))
        greetings <- client.greetSubordinates(ceo.id.get)
      } yield {
        greetings.size shouldBe 2
        greetings.map(_.text) should contain theSameElementsAs List("Hi, tco !", "Hi, engineer !")
      }
    }

    "greet guest" in { (client: GreetingClient) =>
      for {
        greeting <- client.greet[Guest]("Chrome")
      } yield {
        greeting.greetingMethod shouldBe a[GuestGreetingMethod.type]
        greeting.text shouldBe "Hi, guest !"
      }
    }

    "greet bot" in { (client: GreetingClient) =>
      for {
        greeting <- client.greet[Bot]("Googlebot")
      } yield {
        greeting.greetingMethod shouldBe a[BotGreetingMethod.type]
        greeting.text shouldBe "Hi, Googlebot !"
      }
    }
  }
}
