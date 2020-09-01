package ru.otus.sc.user.model

import ru.otus.sc.user.model.UserTag.UserTagId

case class UserTag(id: Option[UserTagId], tagName: String)

object UserTag {
  type UserTagId = Long
}
