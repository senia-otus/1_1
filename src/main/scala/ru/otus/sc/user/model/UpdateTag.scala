package ru.otus.sc.user.model

import ru.otus.sc.user.model.UserTag.UserTagId

case class UpdateUserTagRequest(tag: UserTag)

sealed trait UpdateUserTagResponse
object UpdateUserTagResponse {
  case class Updated(tag: UserTag)      extends UpdateUserTagResponse
  case class NotFound(tagId: UserTagId) extends UpdateUserTagResponse
  case object ErrorNoTagId              extends UpdateUserTagResponse
}
