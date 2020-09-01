package ru.otus.sc.user.model

import ru.otus.sc.user.model.User.UniqueUserId
import ru.otus.sc.user.model.UserTag.UserTagId

case class UpdateTagUserRequest(tagId: UserTagId, uniqueUserId: UniqueUserId)

sealed trait UpdateTagUserResponse
object UpdateTagUserResponse {
  case class TaggedUser(tagId: UserTagId, uniqueUserId: UniqueUserId) extends UpdateTagUserResponse
  case class NotFoundTag(tagId: UserTagId)                            extends UpdateTagUserResponse
}

case class UpdateUntagUserRequest(tagId: UserTagId, uniqueUserId: UniqueUserId)

sealed trait UpdateUntagUserResponse
object UpdateUntagUserResponse {
  case class UntaggedUser(tagId: UserTagId, uniqueUserId: UniqueUserId)
      extends UpdateUntagUserResponse
  case class NotFoundTag(tagId: UserTagId) extends UpdateUntagUserResponse
}
