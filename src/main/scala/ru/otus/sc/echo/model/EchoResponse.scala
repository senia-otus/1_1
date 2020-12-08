package ru.otus.sc.echo.model

/**
  * Response case class for echo service
  * @param message echoing message
  * @param hasError has true value if request is not valid
  */
case class EchoResponse(message: String, hasError: Boolean = false)
