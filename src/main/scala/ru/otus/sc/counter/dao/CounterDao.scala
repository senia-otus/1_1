package ru.otus.sc.counter.dao

trait CounterDao {
  def getCount: String
  def clearCount: String
}
