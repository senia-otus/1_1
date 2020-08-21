package ru.otus.sc.user.model

import ru.otus.sc.auth.model.Token

class User(val id: Long, val name: String, val login: String, val email: String, val password: String, val token: Option[Token], val avatar: Option[String])

object User {
  def empty() = new User(0, "", "", "", "", None, None)
}
