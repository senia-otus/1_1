package ru.otus.sc.countdown.model

import java.util.UUID

sealed trait Countdown

object Countdown {
  type CountdownId    = UUID
  type UpdaterId      = UUID
  type CountdownValue = Long
  type CompareValues  = (CountdownValue, CountdownValue) => Boolean

  case class Tick(id: Option[CountdownId], updater: UpdaterId, value: CountdownValue = 1L)
      extends Countdown
  case class Done(id: Option[CountdownId]) extends Countdown

}
