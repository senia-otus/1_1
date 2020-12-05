package ru.otus.sc.greet.model

import derevo.circe.codec
import derevo.derive

@derive(codec)
case class User(
  id: Option[Id[User]],
  managerId: Option[Id[User]],
  name: String
)
