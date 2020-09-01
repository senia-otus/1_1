package ru.otus.sc.countdown.model

import ru.otus.sc.countdown.model.Countdown.CountdownId

case class GetCountdownRequest(id: CountdownId)

sealed trait GetCountdownResponse
object GetCountdownResponse {
  case class Found(countdown: Countdown) extends GetCountdownResponse
  case class NotFound(id: CountdownId)   extends GetCountdownResponse
}
