package ru.otus.sc.counter.model

import ru.otus.sc.counter.model.Counter.CounterId

case class DeleteCounterRequest(id: CounterId)

sealed trait DeleteCounterResponse
object DeleteCounterResponse {
  case class Deleted(counter: Counter) extends DeleteCounterResponse
  case class NotFound(id: CounterId)   extends DeleteCounterResponse
}
