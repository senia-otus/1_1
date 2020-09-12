package ru.otus.sc.countdown.dao.impl

import ru.otus.sc.countdown.dao.CountdownDao

class CountdownDaoImpl extends CountdownDao {
  private var count: Long = 0

  def clearCountdown(initValue: Long): String = {
    if (initValue > 0) count = initValue
    getCountdown
  }

  def getCountdown: String = {
    if (count > 0) count -= 1
    else count = 0
    count.toString
  }
}
