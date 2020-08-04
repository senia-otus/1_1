package ru.otus.sc.echo.service.impl

import ru.otus.sc.echo.model.{EchoRequest, EchoResponse}
import ru.otus.sc.echo.service.EchoService

/**
  * Implementation of Echo Service
  */
class EchoServiceImpl extends EchoService {

  /**
    * Echo the value
    *
    * @param request request with value to echo
    * @return response with echoed value
    */
  override def echo(request: EchoRequest): EchoResponse = {
    EchoResponse(request.value.toString)
  }
}
