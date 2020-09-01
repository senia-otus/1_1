package ru.otus.sc.user.model

import ru.otus.sc.user.model.User.UniqueUserId

case class GetUserRequest(uniqueId: UniqueUserId)

sealed trait GetUserResponse
object GetUserResponse {
  case class Found(user: User)                extends GetUserResponse
  case class NotFound(uniqueId: UniqueUserId) extends GetUserResponse

}
