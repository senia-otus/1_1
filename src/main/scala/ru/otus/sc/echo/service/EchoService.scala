package ru.otus.sc.echo.service

import ru.otus.sc.echo.model.{EchoRequest, EchoResponse}

trait EchoService {
  def echo(request: EchoRequest): EchoResponse
}
