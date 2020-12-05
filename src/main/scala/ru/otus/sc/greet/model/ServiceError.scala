package ru.otus.sc.greet.model

import derevo.circe.codec
import derevo.derive

sealed trait ServiceError
@derive(codec)
case class FormatError(text: String) extends Exception(text) with ServiceError

sealed trait UserError                     extends ServiceError
@derive(codec)
case class UserNotFoundError(text: String) extends Exception(text) with UserError
@derive(codec)
case class InvalidUserError(text: String)  extends Exception(text) with UserError
