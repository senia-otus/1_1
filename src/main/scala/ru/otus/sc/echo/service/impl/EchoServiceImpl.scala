package ru.otus.sc.echo.service.impl

import ru.otus.sc.echo.dao.EchoDao
import ru.otus.sc.echo.model.{EchoRequest, EchoResponse}
import ru.otus.sc.echo.service.EchoService

class EchoServiceImpl(dao: EchoDao) extends EchoService {
  def ping(request: EchoRequest): EchoResponse =
    request.message match {
      case "Ping"  => EchoResponse(dao.pongMessage)
      case message => EchoResponse(s"${dao.prefix}:$message. ${dao.postfix}")
    }
}
