package ru.otus.sc.echo.dao.impl

import ru.otus.sc.echo.dao.EchoDao

import scala.annotation.tailrec

class EchoDaoImpl extends EchoDao {
  val echoPrefix: String = "Echo reply:"

  def getResponse(request: String, repeatNum: Int): String = {
    s"$echoPrefix ${multiplyResponse(request)()(repeatNum)}"
  }

  //  Can not use collections, so use recursion
  @tailrec
  private def multiplyResponse(response: String)(acc: String = "")(numResponse: Int): String = {
    if (numResponse <= 0) acc.trim
    else multiplyResponse(response)(s"$response $acc")(numResponse - 1)
  }
}
