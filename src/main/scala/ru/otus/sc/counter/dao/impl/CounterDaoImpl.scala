package ru.otus.sc.counter.dao.impl

import ru.otus.sc.counter.dao.CounterDao

import java.util.concurrent.{ConcurrentHashMap, ConcurrentMap}

class CounterDaoImpl extends CounterDao {
  private val _counters: ConcurrentMap[String, Long] =
    new ConcurrentHashMap[String, Long] //mutable.Map[String, Int]

  def increment(key: String): Long =
    _counters.compute(key, (_, value) => value + 1L) //if (value <= 0) 0 else value + 1

  def current(key: String): Long = _counters.getOrDefault(key, -1L)
}
