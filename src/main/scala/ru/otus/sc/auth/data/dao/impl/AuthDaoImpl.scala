package ru.otus.sc.auth.data.dao.impl

import java.time.OffsetDateTime

import akka.http.scaladsl.util.FastFuture
import ru.otus.sc.auth.data.Token
import ru.otus.sc.auth.data.dao.{AuthDao, TokenDao}
import ru.otus.sc.auth.exceptions.{CredentialsNotUniqueException, LoginFailedException}
import ru.otus.sc.auth.model.TokenView
import ru.otus.sc.common.{DB, ImplicitHelpers}
import ru.otus.sc.user.data.UserRepository
import ru.otus.sc.user.data.db.FakeUsersDb
import ru.otus.sc.user.model.User

import scala.concurrent.Future
import scala.util.Success

class AuthDaoImpl extends AuthDao with ImplicitHelpers {

  private lazy val userRepo = UserRepository
  private lazy val tokenDao: TokenDao = new TokenDaoImpl

  override def auth(login: String, password: String): Future[Token] = userRepo.findByLogin(login)
    .flatMap {
      case user :: Nil => tokenDao.generateToken(login + password)
      case Nil => FastFuture.failed(LoginFailedException(login))
      case user :: rest => FastFuture.failed(CredentialsNotUniqueException("was detected not unique username"))
    }


  override def register(login: String, password: String, name: String, email: String): Option[TokenView] = db.save(
    new User(
      id = db.increment(),
      name = name,
      login = login,
      email = email,
      password = password,
      token = Some(TokenView(tokenDao.generateToken(login + password), OffsetDateTime.now().plusDays(1))),
      avatar = None
    )
  ).value.get match {
    case Success(user) => user.token
    case _ => None
  }

}
