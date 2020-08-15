package ru.otus.sc.counter.service.impl

import ru.otus.sc.counter.dao.CounterDao
import ru.otus.sc.counter.model.{CounterRequest, CounterResponse}
import ru.otus.sc.counter.service.CounterService

class CounterServiceImpl(dao: CounterDao) extends CounterService {
  def getCount(request: CounterRequest): CounterResponse = {
    if (!request.clear) CounterResponse(dao.getCount)
    else CounterResponse(dao.clearCount)
  }

}
