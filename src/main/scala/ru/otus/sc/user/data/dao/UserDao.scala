package ru.otus.sc.user.data.dao

import ru.otus.sc.user.model.UserView

import scala.concurrent.Future

trait UserDao {

  def findUser(id: Long): Future[UserView]

}
