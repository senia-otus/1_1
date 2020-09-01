package ru.otus.sc.user.model

case class CreateUserTagRequest(tag: UserTag)

sealed trait CreateUserTagResponse
object CreateUserTagResponse {
  case class Created(tag: UserTag) extends CreateUserTagResponse
  case class Error(error: String)  extends CreateUserTagResponse
}
