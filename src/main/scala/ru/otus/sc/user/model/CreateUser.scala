package ru.otus.sc.user.model

case class CreateUserRequest(user: User)

sealed trait CreateUserResponse
object CreateUserResponse {
  case class Created(user: User)  extends CreateUserResponse
  case class Error(error: String) extends CreateUserResponse
}
