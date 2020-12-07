package ru.otus.sc.greet.service

import ru.otus.sc.greet.dao.GreetingDao
import ru.otus.sc.greet.model._

/**
 * Сервис приветствий
 */
trait GreetingService {
  def greetUser(id: Id[User]): Either[UserNotFoundError, Greeting]
  def greetSubordinates(id: Id[User]): Either[UserNotFoundError, Set[Greeting]]
  def greetBot(bot: Bot): Greeting
  def greetGuest: Greeting
  def findGreetings[A: GreetingMethodTyped](id: Option[Id[A]], text: Option[String]): List[Greeting]
}

object GreetingService {
  def apply(greetingDao: GreetingDao, userService: UserService): GreetingService = {
    new GreetingService {
      override def greetUser(id: Id[User]): Either[UserNotFoundError, Greeting] = {
        userService.find(id).map(user => greetingDao.greet(user))
      }

      override def greetSubordinates(id: Id[User]): Either[UserNotFoundError, Set[Greeting]] = {
        userService.findSubordinates(id).map(_.map(user => greetingDao.greet(user)))
      }

      override def greetBot(bot: Bot): Greeting = {
        greetingDao.greet(bot)
      }

      override def greetGuest: Greeting = {
        greetingDao.greet(Guest())
      }

      override def findGreetings[A: GreetingMethodTyped](id: Option[Id[A]], text: Option[String]): List[Greeting] = {
        greetingDao.findGreetings(id, text)
      }
    }
  }
}
