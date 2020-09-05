package ru.otus.sc.user.data.dao.impl

import ru.otus.sc.common.ImplicitHelpers
import ru.otus.sc.user.data.UserRepository
import ru.otus.sc.user.data.dao.UserDao
import ru.otus.sc.user.model.UserView

import scala.concurrent.Future

class UserDaoImpl extends UserDao with ImplicitHelpers {

  override def findUser(id: Long): Future[UserView] =
    UserRepository
      .findById(id)
      .map { user =>
        new UserView(
          user.id,
          user.name,
          user.login,
          user.email,
          user.avatar
        )
      }

}
