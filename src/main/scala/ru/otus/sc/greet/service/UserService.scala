package ru.otus.sc.greet.service

import ru.otus.sc.greet.dao.UserDao
import ru.otus.sc.greet.model.{ Id, NotFoundError, ServiceError, User }

trait UserService {
  def find(id: Id[User]): Either[ServiceError, User]
  def findSubordinates(id: Id[User]): Either[ServiceError, Set[User]]
}

object UserService {
  def default(dao: UserDao): UserService =
    new UserService {
      override def find(id: Id[User]): Either[ServiceError, User] = {
        dao.find(id).toRight(NotFoundError(s"User not found $id"))
      }

      override def findSubordinates(id: Id[User]): Either[ServiceError, Set[User]] = {
        find(id).map(_ => dao.findSubordinates(id))
      }
    }
}
