package ru.otus.sc.countdown.service

import ru.otus.sc.countdown.model._

trait CountdownService {
  def createCountdown(request: CreateCountdownRequest): CreateCountdownResponse
  def deleteCountdown(request: DeleteCountdownRequest): DeleteCountdownResponse
  def updateCountdown(request: UpdateCountdownRequest): UpdateCountdownResponse
  def getCountdown(request: GetCountdownRequest): GetCountdownResponse
  def findCountdowns(request: FindCountdownsRequest): FindCountdownsResponse
}
