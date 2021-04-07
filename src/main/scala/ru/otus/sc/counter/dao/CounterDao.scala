package ru.otus.sc.counter.dao

/**
  * Доменный репозиторий для расчета счетчика вызовов
  */
trait CounterDao {
  def increment(key: String): Long
  def current(key: String): Long
}
