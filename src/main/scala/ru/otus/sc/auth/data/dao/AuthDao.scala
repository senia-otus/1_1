package ru.otus.sc.auth.data.dao

import ru.otus.sc.auth.model.TokenView

import scala.concurrent.Future

trait AuthDao {

  def auth(login: String, password: String): Future[TokenView]
  def register(login: String, password: String, name: String, email: String): Future[TokenView]

}
