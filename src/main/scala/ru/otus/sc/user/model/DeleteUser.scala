package ru.otus.sc.user.model

import ru.otus.sc.user.model.User.UniqueUserId

case class DeleteUserRequest(uniqueId: UniqueUserId)

sealed trait DeleteUserResponse
object DeleteUserResponse {
  case class Deleted(user: User)              extends DeleteUserResponse
  case class NotFound(uniqueId: UniqueUserId) extends DeleteUserResponse
}
