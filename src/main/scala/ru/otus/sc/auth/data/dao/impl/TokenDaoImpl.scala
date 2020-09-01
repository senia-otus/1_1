package ru.otus.sc.auth.data.dao.impl

import pdi.jwt.Jwt
import ru.otus.sc.auth.data.dao.TokenDao
import ru.otus.sc.auth.data.{Token, TokenRepository}
import ru.otus.sc.common.ImplicitHelpers

import scala.concurrent.Future

class TokenDaoImpl extends TokenDao with ImplicitHelpers {

  private val repo = TokenRepository

  override def generateToken(str: String): Future[Token] = Some(Jwt.encode(str))
    .map { salt =>
      for {
        _ <- repo.upsert(Token(salt))
        token <- repo.findByToken(salt)
      } yield token
    }
    .get

}
