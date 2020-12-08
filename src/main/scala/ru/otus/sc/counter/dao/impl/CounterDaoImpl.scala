package ru.otus.sc.counter.dao.impl

import ru.otus.sc.counter.dao.CounterDao

/**
  * Implementation for @see CounterDao
  */
class CounterDaoImpl extends CounterDao {
  private var count: Int = 0

  /**
    * Reset counter to zero value
    */
  override def resetCounter(): Unit = count = 0

  /**
    * Add to counter step value
    * @param step value to add
    * @return result count
    */
  override def nextStep(step: Int): Int = {
    count += step
    count
  }

  /**
    * Subtract to counter step value
    * @param step value to subtract
    * @return result count
    */
  override def prevStep(step: Int): Int = {
    count -= step
    count
  }

  /**
    * Return current counter value
    * @return current value
    */
  override def getCurrentValue(): Int = count

}
