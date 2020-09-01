package ru.otus.sc.user.model

import java.util.UUID

import ru.otus.sc.user.model.User.UniqueUserId
import ru.otus.sc.user.model.UserTag.UserTagId

case class UserName(
    firstName: String,
    lastName: Option[String],
    middleName: Option[String],
    patronymicName: Option[String],
    title: Option[String]
)

case class User(
    // id related to DB table
    id: Option[Long],
    // unique id for whole system
    uniqueId: Option[UniqueUserId],
    name: UserName,
    age: Option[Int],
    tags: Set[UserTagId]
)

object User {
  type UniqueUserId = UUID
}
