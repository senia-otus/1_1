package ru.otus.sc.counter.model

case class CreateCounterRequest(counter: Counter)

sealed trait CreateCounterResponse
object CreateCounterResponse {
  case class Created(counter: Counter) extends CreateCounterResponse
  case class Error(error: String)      extends CreateCounterResponse
}
