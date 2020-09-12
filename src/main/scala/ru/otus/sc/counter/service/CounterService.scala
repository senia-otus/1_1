package ru.otus.sc.counter.service

import ru.otus.sc.counter.model.{CounterRequest, CounterResponse}

trait CounterService {
  def getCount(request: CounterRequest): CounterResponse
}
