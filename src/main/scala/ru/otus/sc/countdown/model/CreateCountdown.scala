package ru.otus.sc.countdown.model

case class CreateCountdownRequest(countdown: Countdown)

sealed trait CreateCountdownResponse
object CreateCountdownResponse {
  case class Created(countdown: Countdown) extends CreateCountdownResponse
  case class Error(error: String)          extends CreateCountdownResponse
}
