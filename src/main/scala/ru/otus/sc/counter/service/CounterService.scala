package ru.otus.sc.counter.service

import ru.otus.sc.counter.model._

trait CounterService {
  def createCounter(request: CreateCounterRequest): CreateCounterResponse
  def deleteCounter(request: DeleteCounterRequest): DeleteCounterResponse
  def updateCounter(request: UpdateCounterRequest): UpdateCounterResponse
  def getCounter(request: GetCounterRequest): GetCounterResponse
  def findCounters(request: FindCountersRequest): FindCountersResponse
}
