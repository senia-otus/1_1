package ru.otus.sc.counter.dao

/**
  * Trait for counter dao
  * Handle to work counter: add, reset and get value of counter
  */
trait CounterDao {
  def resetCounter(): Unit
  def nextStep(step: Int): Int
  def prevStep(step: Int): Int
  def getCurrentValue(): Int
}
