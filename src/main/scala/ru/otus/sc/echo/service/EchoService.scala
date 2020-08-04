package ru.otus.sc.echo.service

import ru.otus.sc.echo.model.{EchoRequest, EchoResponse}

/**
  * Service to echo value
  */
trait EchoService {

  /**
    * Echo the value
    *
    * @param request request with value to echo
    * @return response with echoed value
    */
  def echo(request: EchoRequest): EchoResponse
}
