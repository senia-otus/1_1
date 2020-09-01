package ru.otus.sc.countdown.model

import ru.otus.sc.countdown.model.Countdown.{CompareValues, CountdownValue, UpdaterId}

sealed trait FindCountdownsRequest
object FindCountdownsRequest {
  case class ByValue(value: CountdownValue, predicate: CompareValues) extends FindCountdownsRequest
  case class ByUpdater(updater: UpdaterId)                            extends FindCountdownsRequest
  case class AllDone()                                                extends FindCountdownsRequest
  case class AllNonDone()                                             extends FindCountdownsRequest
}

sealed trait FindCountdownsResponse
object FindCountdownsResponse {
  case class Result(countdowns: Seq[Countdown]) extends FindCountdownsResponse
}
