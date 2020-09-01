package ru.otus.sc.counter.model

import java.time.LocalDateTime
import java.util.UUID

import ru.otus.sc.counter.model.Counter.{CounterId, CounterTime, CounterValue}

case class Counter(id: Option[CounterId], timestamp: Option[CounterTime], value: CounterValue = 1L)
object Counter {
  type CounterId    = UUID
  type CounterValue = Long
  type CounterTime  = LocalDateTime
}
