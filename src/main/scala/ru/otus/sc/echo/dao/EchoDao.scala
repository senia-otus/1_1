package ru.otus.sc.echo.dao

/**
  * Trait of echo dao
  */
trait EchoDao {
  def echoMessage(message: String, repeatCount: Int): String
}
