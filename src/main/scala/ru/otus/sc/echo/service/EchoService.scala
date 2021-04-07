package ru.otus.sc.echo.service

import ru.otus.sc.echo.model.{EchoRequest, EchoResponse}

/**
  * Сервис проверки "жизни" микросервиса:
  * отправляется запрос через метод ping и должен быть получен стандартный ответ
  */
trait EchoService {
  def ping(request: EchoRequest): EchoResponse
}
