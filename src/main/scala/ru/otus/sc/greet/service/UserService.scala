package ru.otus.sc.greet.service

import ru.otus.sc.greet.dao.UserDao
import ru.otus.sc.greet.model._

/**
 * Сервис создания и получения пользователей
 */
trait UserService {
  def create(user: User): Either[UserError, User]
  def find(id: Id[User]): Either[UserNotFoundError, User]
  def findSubordinates(id: Id[User]): Either[UserNotFoundError, Set[User]]
}

object UserService {
  def apply(dao: UserDao): UserService =
    new UserService {
      override def create(user: User): Either[UserError, User] = {
        user.id match {
          //id должен отсутствовать
          case Some(id) => Left(InvalidUserError(s"Cannot create user with id $id"))
          //если managerId заполнен, такой пользователь должен присутствовать в системе
          case None     => user.managerId.flatMap(dao.find) match {
              case None if user.managerId.isDefined => Left(UserNotFoundError(s"Cannot find manager ${user.managerId}"))
              case _                                => Right(dao.create(user))
            }
        }
      }

      override def find(id: Id[User]): Either[UserNotFoundError, User] = {
        dao.find(id).toRight(UserNotFoundError(s"User not found $id"))
      }

      override def findSubordinates(id: Id[User]): Either[UserNotFoundError, Set[User]] = {
        find(id).map(_ => dao.findSubordinates(id))
      }
    }
}
