package ru.otus.sc.user.dao

import ru.otus.sc.user.model.User

import scala.concurrent.Future

trait UserDao {

  def findUser(id: Long): Future[User]

}
