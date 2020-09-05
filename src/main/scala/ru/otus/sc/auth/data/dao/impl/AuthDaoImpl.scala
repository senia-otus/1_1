package ru.otus.sc.auth.data.dao.impl

import akka.http.scaladsl.util.FastFuture
import ru.otus.sc.auth.data.dao.{AuthDao, TokenDao}
import ru.otus.sc.auth.exceptions.{CredentialsNotUniqueException, LoginFailedException, TokenNotFoundException}
import ru.otus.sc.auth.model.TokenView
import ru.otus.sc.common.ImplicitHelpers
import ru.otus.sc.user.data.{User, UserRepository}

import scala.concurrent.Future

class AuthDaoImpl extends AuthDao with ImplicitHelpers {

  private lazy val userRepo           = UserRepository
  private lazy val tokenDao: TokenDao = new TokenDaoImpl

  override def auth(login: String, password: String): Future[TokenView] =
    userRepo
      .findAllByLogin(login)
      .flatMap {
        case user :: Nil => tokenDao.generateToken(user.login + user.password)
        case Nil         => FastFuture.failed(LoginFailedException(login))
        case user :: rest :: Nil =>
          FastFuture.failed(CredentialsNotUniqueException("was detected not unique username"))
      }
      .map { token =>
        TokenView(token.value, token.expiredAt)
      }

  override def register(
      login: String,
      password: String,
      name: String,
      email: String
  ): Future[TokenView] =
    tokenDao
      .generateToken(login + password)
      .flatMap { token =>
        userRepo
          .save(
            User(
              name = name,
              login = login,
              email = email,
              password = password,
              tokenId = Some(token.value),
              avatar = None
            )
          )
          .map { u => (token, u) }
      }
      .flatMap { tuple =>
        val (token, user) = tuple
        user.tokenId match {
          case Some(_) => FastFuture.successful(TokenView(token.value, token.expiredAt))
          case _ =>
            FastFuture.failed(TokenNotFoundException(s"token for user_id -> ${user.id} not found"))
        }
      }

}
