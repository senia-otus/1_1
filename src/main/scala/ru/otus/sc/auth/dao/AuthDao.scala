package ru.otus.sc.auth.dao

import ru.otus.sc.auth.model.{AuthResponse, Token}

trait AuthDao {

  def auth(login: String, password: String): Option[Token]
  def register(login: String, password: String, name: String, email: String): Option[Token]

}
