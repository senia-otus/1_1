package ru.otus.sc.user.model

import ru.otus.sc.user.model.User.UniqueUserId

case class UpdateUserRequest(user: User)

sealed trait UpdateUserResponse
object UpdateUserResponse {
  case class Updated(user: User)              extends UpdateUserResponse
  case class NotFound(uniqueId: UniqueUserId) extends UpdateUserResponse
  case object ErrorNoUniqueId                 extends UpdateUserResponse
  case object ErrorWrongId                    extends UpdateUserResponse
}
