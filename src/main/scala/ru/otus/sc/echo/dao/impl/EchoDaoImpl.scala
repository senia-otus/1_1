package ru.otus.sc.echo.dao.impl

import ru.otus.sc.echo.dao.EchoDao

class EchoDaoImpl extends EchoDao {
  var pingMessage: String = "Ping"
  val pongMessage: String = "Pong"

  val prefix: String  = "Echo"
  val postfix: String = "Good day, m8!"

}
