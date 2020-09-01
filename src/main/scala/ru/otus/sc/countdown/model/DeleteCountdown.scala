package ru.otus.sc.countdown.model

import ru.otus.sc.countdown.model.Countdown.CountdownId

case class DeleteCountdownRequest(id: CountdownId)

sealed trait DeleteCountdownResponse
object DeleteCountdownResponse {
  case class Deleted(countdown: Countdown) extends DeleteCountdownResponse
  case class NotFound(id: CountdownId)     extends DeleteCountdownResponse
}
