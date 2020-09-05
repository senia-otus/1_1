package ru.otus.sc.user.model

class UserView(
    val id: Long,
    val name: String,
    val login: String,
    val email: String,
    val avatar: Option[String]
)

object UserView {
  def empty() = new UserView(0, "", "", "", None)
}
