package ru.otus.sc.user.model

import ru.otus.sc.user.model.UserTag.UserTagId

case class DeleteUserTagRequest(tagId: UserTagId)

sealed trait DeleteUserTagResponse
object DeleteUserTagResponse {
  case class Deleted(tag: UserTag)      extends DeleteUserTagResponse
  case class NotFound(tagId: UserTagId) extends DeleteUserTagResponse
}
