package ru.otus.sc.user.model

import ru.otus.sc.user.model.UserTag.UserTagId

case class GetUserTagRequest(tagId: UserTagId)

sealed trait GetUserTagResponse
object GetUserTagResponse {
  case class Found(tag: UserTag)        extends GetUserTagResponse
  case class NotFound(tagId: UserTagId) extends GetUserTagResponse
}
