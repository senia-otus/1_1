package ru.otus.sc.echo.service.impl

import ru.otus.sc.echo.dao.EchoDao
import ru.otus.sc.echo.model.{EchoRequest, EchoResponse, EchoResponseAnswer, EchoResponseError}
import ru.otus.sc.echo.service.EchoService

class EchoServiceImpl(dao: EchoDao) extends EchoService {

  def echo(request: EchoRequest): EchoResponse =
    if (request.repeatNum > 0 & request.repeatNum <= 5)
      EchoResponseAnswer(s"${dao.getResponse(request.echoRequest, request.repeatNum)}")
    else EchoResponseError("Number of replies has to be from 1 to 5")
}
