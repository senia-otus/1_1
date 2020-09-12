package ru.otus.sc.countdown.model

sealed trait CountdownResponse {
  val countValue: String
  def isDone: Boolean
}

case class CountdownCountResponse(countValue: String) extends CountdownResponse {
  def isDone = false
}
case class CountdownDoneResponse(countValue: String) extends CountdownResponse {
  def isDone = true
}
