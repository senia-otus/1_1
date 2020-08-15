package ru.otus.sc.echo.dao

trait EchoDao {
  def echoPrefix: String
  def getResponse(request: String, repeatNum: Int): String
}
