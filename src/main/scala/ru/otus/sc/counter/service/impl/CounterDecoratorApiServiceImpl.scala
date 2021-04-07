package ru.otus.sc.counter.service.impl

import ru.otus.sc.counter.dao.CounterDao
import ru.otus.sc.counter.model.{ApiRequest1, ApiRequest2, ApiResponse1, ApiResponse2}
import ru.otus.sc.counter.service.ApiService

/**
  * Декоратор для "захвата" запросов с целью расчета счетчика и делегирование вызовов в api
  * @param api
  * @param dao
  */
class CounterDecoratorApiServiceImpl(api: ApiService, dao: CounterDao) extends ApiService {
  def method1(request: ApiRequest1): ApiResponse1 = {
    val counter = dao.increment("method1")
    val res     = api.method1(request)
    res.copy(counter = counter)
  }

  def method2(request: ApiRequest2): ApiResponse2 = {
    val counter = dao.increment("method2")
    val res     = api.method2(request)
    res.copy(counter = counter)
  }
}
