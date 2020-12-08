package ru.otus.sc.echo.service.impl

import ru.otus.sc.echo.dao.EchoDao
import ru.otus.sc.echo.model.{EchoRequest, EchoResponse}
import ru.otus.sc.echo.service.EchoService

/**
  * Implementation of @see EchoService
  * @param dao DAO for echo service @see EchoDao
  */
class EchoServiceImpl(dao: EchoDao) extends EchoService {

  /**
    * Method is receive message, check validation and delegate to dao
    * @param request echo request type @see EchoRequest
    * @return echo response type @see EchoResponse
    */
  override def echo(request: EchoRequest): EchoResponse = {
    try {
      require(request.repeatCount >= 0)
      require(
        request.message != null && (!request.message.isEmpty || !request.message.isBlank)
      )
      EchoResponse(dao.echoMessage(request.message, request.repeatCount))
    } catch {
      case e: IllegalArgumentException => {
        lazy val exceptionMessage =
          "Not valid request. Message cannot be empty or null and repeatCount must be positive number"
        EchoResponse(exceptionMessage, true)
      }
    }
  }

}
