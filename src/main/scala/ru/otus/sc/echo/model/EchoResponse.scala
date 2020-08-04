package ru.otus.sc.echo.model

/**
  * Response with echoed value
  *
  * @param value echoed value
  */
case class EchoResponse(value: String) {

  /**
    * Print value to console
    */
  def consolePrint(): Unit = {
    println(this.value)
  }
}
