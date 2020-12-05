package ru.otus.sc.greet.service

import ru.otus.sc.greet.dao.GreetingDao
import ru.otus.sc.greet.model._

trait GreetingService {
  def greetUser(id: Id[User]): Either[UserNotFoundError, Greeting[User]]
  def greetSubordinates(id: Id[User]): Either[UserNotFoundError, Set[Greeting[User]]]
  def greetBot(bot: Bot): Greeting[Bot]
  def greetGuest: Greeting[Guest]
}

object GreetingService {
  def apply(greetingDao: GreetingDao, userService: UserService): GreetingService = {
    new GreetingService {
      def greetUser(id: Id[User]): Either[UserNotFoundError, Greeting[User]] = {
        userService.find(id).map(user => greetingDao.greet(user))
      }

      def greetSubordinates(id: Id[User]): Either[UserNotFoundError, Set[Greeting[User]]] = {
        userService.findSubordinates(id).map(_.map(user => greetingDao.greet(user)))
      }

      def greetBot(bot: Bot): Greeting[Bot] = {
        greetingDao.greet(bot)
      }

      def greetGuest: Greeting[Guest] = {
        greetingDao.greet(Guest())
      }
    }
  }
}
