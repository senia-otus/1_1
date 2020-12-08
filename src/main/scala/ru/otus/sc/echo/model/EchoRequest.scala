package ru.otus.sc.echo.model

/**
  * Request case class for echo service
  * @param message - parameter to echo. cannot be empty
  * @param repeatCount - number of repetitions. only positive value
  */
case class EchoRequest(message: String, repeatCount: Int = 1)
