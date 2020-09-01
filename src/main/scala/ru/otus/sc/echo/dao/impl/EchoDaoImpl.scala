package ru.otus.sc.echo.dao.impl

import ru.otus.sc.echo.dao.EchoDao

class EchoDaoImpl extends EchoDao {
  val echoPrefix: String = "Echo reply: "

  def getResponse(request: String, repeatNum: Int): String = {
    // use collection for making request
    List.fill(repeatNum)(request).mkString(echoPrefix, " ", "")
  }
}
