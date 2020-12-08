package ru.otus.sc.counter.service

import ru.otus.sc.counter.model.{CounterRequest, CounterResponse}

/**
  * Trait for counter service
  */
trait CounterService {

  def processCounter(request: CounterRequest): CounterResponse
}
