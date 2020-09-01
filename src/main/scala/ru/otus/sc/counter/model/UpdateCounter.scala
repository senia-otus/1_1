package ru.otus.sc.counter.model

import ru.otus.sc.counter.model.Counter.CounterId

case class UpdateCounterRequest(id: CounterId)

sealed trait UpdateCounterResponse
object UpdateCounterResponse {
  case class Updated(counter: Counter) extends UpdateCounterResponse
  case class NotFound(id: CounterId)   extends UpdateCounterResponse
}
