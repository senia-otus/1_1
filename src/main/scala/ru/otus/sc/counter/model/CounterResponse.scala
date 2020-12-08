package ru.otus.sc.counter.model

/**
 * Response counter type
 * @param currentValue result value after process
 * @param message message for exception case
 */
case class CounterResponse(currentValue: Int, message: String = "")
