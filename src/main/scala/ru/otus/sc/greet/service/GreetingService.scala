package ru.otus.sc.greet.service

import ru.otus.sc.greet.dao.GreetingDao
import ru.otus.sc.greet.model._

/**
 * Сервис приветствий
 */
trait GreetingService {
  def greetUser(id: Id[User]): Either[UserNotFoundError, Greeting[User]]
  def greetSubordinates(id: Id[User]): Either[UserNotFoundError, Set[Greeting[User]]]
  def greetBot(bot: Bot): Greeting[Bot]
  def greetGuest: Greeting[Guest]
  def findGreetings[A: GreetingMethod](id: Option[Id[A]], text: Option[String]): List[Greeting[A]]
}

object GreetingService {
  def apply(greetingDao: GreetingDao, userService: UserService): GreetingService = {
    new GreetingService {
      override def greetUser(id: Id[User]): Either[UserNotFoundError, Greeting[User]] = {
        userService.find(id).map(user => greetingDao.greet(user))
      }

      override def greetSubordinates(id: Id[User]): Either[UserNotFoundError, Set[Greeting[User]]] = {
        userService.findSubordinates(id).map(_.map(user => greetingDao.greet(user)))
      }

      override def greetBot(bot: Bot): Greeting[Bot] = {
        greetingDao.greet(bot)
      }

      override def greetGuest: Greeting[Guest] = {
        greetingDao.greet(Guest())
      }

      override def findGreetings[A: GreetingMethod](id: Option[Id[A]], text: Option[String]): List[Greeting[A]] = {
        greetingDao.findGreetings(id, text)
      }
    }
  }
}
