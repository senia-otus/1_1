package ru.otus.sc.counter.dao.impl

import ru.otus.sc.counter.dao.CounterDao

class CounterDaoImpl extends CounterDao {
  private var count: Long = 0

  def clearCount: String = {
    count = 0
    getCount
  }

  def getCount: String = {
    count += 1
    count.toString
  }
}
