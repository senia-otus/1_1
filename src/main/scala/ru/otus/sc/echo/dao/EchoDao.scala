package ru.otus.sc.echo.dao

/**
  * Доменный репозиторий для формирования echo-ответа
  */
trait EchoDao {
  def pingMessage: String
  def pongMessage: String

  def prefix: String
  def postfix: String
}
