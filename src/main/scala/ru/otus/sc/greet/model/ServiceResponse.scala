package ru.otus.sc.greet.model

case class ServiceResponse(answer: Either[String, String])

object ServiceResponse {

  def bad(v: String): ServiceResponse  = ServiceResponse(Left(v))
  def good(v: String): ServiceResponse = ServiceResponse(Right(v))

}
