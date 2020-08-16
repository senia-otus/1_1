package ru.otus.sc.echo.model

sealed trait EchoResponse {
  def isValid: Boolean
}

case class EchoAnswerResponse(answer: String) extends EchoResponse {
  def isValid = true
}
case class EchoErrorResponse(error: String) extends EchoResponse {
  def isValid = false
}
