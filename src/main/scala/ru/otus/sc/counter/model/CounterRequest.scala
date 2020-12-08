package ru.otus.sc.counter.model

/**
  * Parent request type
  */
sealed trait CounterRequest
object CounterRequest {

  /**
   * Type for reset counter
   */
  case class ResetCounterRequest() extends CounterRequest

  /**
   * Add to counter next (or more) value
   * @param step value to add in counter
   */
  case class NextValueRequest(step: Int = 1) extends CounterRequest

  /**
   * Change counter to previous (or less) value
   * @param step value to subtract in counter
   */
  case class PrevValueRequest(step: Int = 1) extends CounterRequest

  case class CurrentValueRequest() extends CounterRequest
}
