package ru.otus.sc.user.dao.impl

import ru.otus.sc.common.{DB, ImplicitHelpers}
import ru.otus.sc.user.dao.UserDao
import ru.otus.sc.user.db.FakeUsersDb
import ru.otus.sc.user.model.User

import scala.concurrent.Future

class UserDaoImpl extends UserDao with ImplicitHelpers {

  private lazy val db: DB[User, Long] = FakeUsersDb

  override def findUser(id: Long): Future[User] = db.all().map {
    users => users.find(_.id == id).get
  }

}
