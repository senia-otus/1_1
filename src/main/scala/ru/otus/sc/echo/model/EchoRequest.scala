package ru.otus.sc.echo.model

/**
  * DTO-модель запроса
  * если сообщение не задано то используется Ping
  * @param message сообщение
  */
case class EchoRequest(message: String = "Ping")
