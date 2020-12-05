package ru.otus.sc.greet.model

case class User(
  id: Option[Id[User]],
  managerId: Option[Id[User]],
  name: String
)
