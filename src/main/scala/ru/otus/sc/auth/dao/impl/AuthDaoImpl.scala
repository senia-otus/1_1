package ru.otus.sc.auth.dao.impl

import java.time.OffsetDateTime

import ru.otus.sc.auth.dao.{AuthDao, TokenDao}
import ru.otus.sc.auth.model.Token
import ru.otus.sc.common.{DB, ImplicitHelpers}
import ru.otus.sc.user.db.FakeUsersDb
import ru.otus.sc.user.model.User

import scala.util.Success

class AuthDaoImpl extends AuthDao with ImplicitHelpers {

  private lazy val db: DB[User, Long] = FakeUsersDb
  private lazy val tokenDao: TokenDao = new TokenDaoImpl

  override def auth(login: String, password: String): Option[Token] = db.all().value.get match {
    case Success(users) => users
      .find { user => user.password == password && user.login == login }
      .map(u => Token(tokenDao.generateToken(u.login + u.password), OffsetDateTime.now().plusDays(1)))
    case _ => None
  }

  override def register(login: String, password: String, name: String, email: String): Option[Token] = db.save(
    new User(
      id = db.increment(),
      name = name,
      login = login,
      email = email,
      password = password,
      token = Some(Token(tokenDao.generateToken(login + password), OffsetDateTime.now().plusDays(1))),
      avatar = None
    )
  ).value.get match {
    case Success(user) => user.token
    case _ => None
  }

}
