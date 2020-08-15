package ru.otus.sc.auth.dao.impl

import java.time.OffsetDateTime

import ru.otus.sc.auth.dao.db.FakeDb
import ru.otus.sc.auth.dao.{AuthDao, TokenDao}
import ru.otus.sc.auth.model.{Token, User}

class AuthDaoImpl extends AuthDao {

  private lazy val db = new FakeDb
  private lazy val tokenDao: TokenDao = new TokenDaoImpl

  override def auth(login: String, password: String): Option[Token] = db.getAllUsers()
    .find { user => user.password == password && user.login == login } match {
    case Some(_) => Some(Token(tokenDao.generateToken(login + password), OffsetDateTime.now().plusDays(1)))
    case None => None
  }

  override def register(login: String, password: String, name: String, email: String): Option[Token] = {
    val token = Some(Token(tokenDao.generateToken(login + password), OffsetDateTime.now().plusDays(1)))

    db.insertUser(
      User(
        email = email,
        login = login,
        password = password, name = name,
        token = token
      )
    )

    token
  }

}
