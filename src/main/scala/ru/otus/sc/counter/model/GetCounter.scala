package ru.otus.sc.counter.model

import ru.otus.sc.counter.model.Counter.CounterId

case class GetCounterRequest(id: CounterId)

sealed trait GetCounterResponse
object GetCounterResponse {
  case class Found(counter: Counter) extends GetCounterResponse
  case class NotFound(id: CounterId) extends GetCounterResponse
}
