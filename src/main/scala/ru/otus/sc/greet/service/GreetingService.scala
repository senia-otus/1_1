package ru.otus.sc.greet.service

import ru.otus.sc.greet.dao.GreetingDao
import ru.otus.sc.greet.model._

trait GreetingService {
  def greetUser(id: Id[User]): Either[ServiceError, Greeting[User]]
  def greetSubordinates(id: Id[User]): Either[ServiceError, Set[Greeting[User]]]
  def greetBot(bot: Bot): Greeting[Bot]
  def greetGuest: Greeting[Guest]
}

object GreetingService {
  def default(greetingDao: GreetingDao, userService: UserService): GreetingService = {
    new GreetingService {
      def greetUser(id: Id[User]): Either[ServiceError, Greeting[User]] = {
        userService.find(id).map(user => greetingDao.greet(user))
      }

      def greetSubordinates(id: Id[User]): Either[ServiceError, Set[Greeting[User]]] = {
        userService.findSubordinates(id).map(_.map(user => greetingDao.greet(user)))
      }

      def greetBot(bot: Bot): Greeting[Bot] = {
        greetingDao.greet(bot)
      }

      def greetGuest: Greeting[Guest] = {
        greetingDao.greet(Guest)
      }
    }
  }
}
