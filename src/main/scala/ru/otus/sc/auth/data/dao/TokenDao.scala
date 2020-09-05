package ru.otus.sc.auth.data.dao

import ru.otus.sc.auth.data.Token

import scala.concurrent.Future

trait TokenDao {

  def generateToken(str: String): Future[Token]

}
