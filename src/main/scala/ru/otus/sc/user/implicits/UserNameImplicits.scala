package ru.otus.sc.user.implicits

import ru.otus.sc.user.model.User

object UserNameImplicits {
  implicit def userNameToString(user: User): String =
    List(
      user.name.title,
      Some(user.name.firstName),
      user.name.middleName,
      user.name.patronymicName,
      user.name.lastName
    )
      .collect { case Some(name) => name }
      .mkString(" ")
}
