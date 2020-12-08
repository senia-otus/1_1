package ru.otus.sc.counter.service.impl

import ru.otus.sc.counter.dao.CounterDao
import ru.otus.sc.counter.model.CounterRequest.{CurrentValueRequest, NextValueRequest, PrevValueRequest, ResetCounterRequest}
import ru.otus.sc.counter.model._
import ru.otus.sc.counter.service.CounterService

/**
  * Impementation @see CounterService
  * @param dao handle command @see CounterDao
  */
class CounterServiceImpl(dao: CounterDao) extends CounterService {

  /**
    * Method is change counter by command: reset counter to default zero,
    * add and subtract value to counter, get current value
    * @param request command for process @see CounterRequest
    * @return counter response type @see CounterResponse
    */
  override def processCounter(request: CounterRequest): CounterResponse = {
    request match {
      case ResetCounterRequest() => {
        dao.resetCounter()
        CounterResponse(0)
      }
      case NextValueRequest(step) => CounterResponse(dao.nextStep(step))
      case PrevValueRequest(step) => {
        val prevResult = dao.prevStep(step)
        val checkCounter: String = if (prevResult < 0) {
          dao.resetCounter() // todo хорошо ли тут сбрасывать счетчик?
          lazy val errorMessage: String =
            "After process counter is negative value. Counter is reset."
          errorMessage
        } else ""
        CounterResponse(dao.getCurrentValue(), checkCounter)
      }
      case CurrentValueRequest() => CounterResponse(dao.getCurrentValue())
    }

  }
}
