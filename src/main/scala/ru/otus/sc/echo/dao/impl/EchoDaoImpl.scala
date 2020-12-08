package ru.otus.sc.echo.dao.impl

import ru.otus.sc.echo.dao.EchoDao

/**
 * Implementation of @see EchoDao
 */
class EchoDaoImpl extends EchoDao{

  /**
   * Method echo message and repeat his repeats it the specified number of times
   * @param message string for echo
   * @param repeatCount
   * @return echoing message
   */
  override def echoMessage(message: String, repeatCount: Int): String = message.repeat(repeatCount)


}
