package ru.otus.sc.echo.model

sealed trait EchoResponse {
  def isValid: Boolean
}

case class EchoResponseAnswer(answer: String) extends EchoResponse {
  def isValid = true
}
case class EchoResponseError(error: String) extends EchoResponse {
  def isValid = false
}
