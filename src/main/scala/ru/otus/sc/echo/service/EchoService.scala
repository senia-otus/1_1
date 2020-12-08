package ru.otus.sc.echo.service

import ru.otus.sc.echo.model.{EchoRequest, EchoResponse}

/**
  * Trait of echo service
  */
trait EchoService {

  def echo(request: EchoRequest): EchoResponse
}
