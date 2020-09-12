package ru.otus.sc.countdown.dao

trait CountdownDao {
  def getCountdown: String
  def clearCountdown(initValue: Long): String
}
