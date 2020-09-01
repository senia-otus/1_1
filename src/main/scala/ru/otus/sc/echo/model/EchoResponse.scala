package ru.otus.sc.echo.model

sealed trait EchoResponse
object EchoResponse {
  case class Answer(answer: String) extends EchoResponse
  case class Error(error: String)   extends EchoResponse
}
