package ru.otus.sc

import ru.otus.sc.`lazy`.dao.impl.LazyDaoImpl
import ru.otus.sc.`lazy`.model.{LazyRequest, LazyResponse}
import ru.otus.sc.`lazy`.service.LazyService
import ru.otus.sc.`lazy`.service.impl.LazyServiceImpl
import ru.otus.sc.counter.dao.impl.CounterDaoImpl
import ru.otus.sc.counter.model.{ApiRequest1, ApiRequest2, ApiResponse1, ApiResponse2}
import ru.otus.sc.counter.service.ApiService
import ru.otus.sc.counter.service.impl.{ApiServiceImpl, CounterDecoratorApiServiceImpl}
import ru.otus.sc.echo.dao.impl.EchoDaoImpl
import ru.otus.sc.echo.model.{EchoRequest, EchoResponse}
import ru.otus.sc.echo.service.EchoService
import ru.otus.sc.echo.service.impl.EchoServiceImpl
import ru.otus.sc.greet.dao.impl.GreetingDaoImpl
import ru.otus.sc.greet.model.{GreetRequest, GreetResponse}
import ru.otus.sc.greet.service.GreetingService
import ru.otus.sc.greet.service.impl.GreetingServiceImpl
import ru.otus.sc.storage.dao.impl.StorageDaoImpl
import ru.otus.sc.storage.model.{
  StorageDeleteBulkRequest,
  StorageDeleteBulkResponse,
  StorageDeleteRequest,
  StorageDeleteResponse,
  StorageFindBulkRequest,
  StorageFindBulkResponse,
  StorageFindRequest,
  StorageFindResponse,
  StorageUpdateBulkRequest,
  StorageUpdateBulkResponse,
  StorageUpdateRequest,
  StorageUpdateResponse
}
import ru.otus.sc.storage.service.StorageService
import ru.otus.sc.storage.service.impl.StorageServiceImpl

trait App {

  /**
    * HelloWord
    * @param request запрос
    * @return ответ
    */
  def greet(request: GreetRequest): GreetResponse

  /**
    * Echo-сервис
    * @param request запрос
    * @return ответ
    */
  def ping(request: EchoRequest): EchoResponse

  /**
    * Storage - хранилище значений по заранее заданному списку ключей
    * @param request запрос
    * @return ответ
    */
  def findBy(request: StorageFindRequest): StorageFindResponse
  def findBy(request: StorageFindBulkRequest): StorageFindBulkResponse
  def update(request: StorageUpdateRequest): StorageUpdateResponse
  def updateBulk(request: StorageUpdateBulkRequest): StorageUpdateBulkResponse
  def delete(request: StorageDeleteRequest): StorageDeleteResponse
  def delete(request: StorageDeleteBulkRequest): StorageDeleteBulkResponse

  /**
    * Lazy - получение лениво вычисляемого значения
    * @param request запрос
    * @return ответ
    */
  def getLazyValue(request: LazyRequest): LazyResponse

  /**
    * Counter - api с поддержкой счетчика вызовов
    * @param request
    * @return
    */
  def method1(request: ApiRequest1): ApiResponse1
  def method2(request: ApiRequest2): ApiResponse2
}

object App {
  private class AppImpl(
      greeting: GreetingService,
      echo: EchoService,
      storage: StorageService,
      `lazy`: LazyService,
      api: ApiService
  ) extends App {
    def greet(request: GreetRequest): GreetResponse = greeting.greet(request)

    def ping(request: EchoRequest): EchoResponse = echo.ping(request)

    def findBy(request: StorageFindRequest): StorageFindResponse         = storage.findBy(request)
    def findBy(request: StorageFindBulkRequest): StorageFindBulkResponse = storage.findBy(request)
    def update(request: StorageUpdateRequest): StorageUpdateResponse     = storage.update(request)
    def updateBulk(request: StorageUpdateBulkRequest): StorageUpdateBulkResponse =
      storage.updateBulk(request)
    def delete(request: StorageDeleteRequest): StorageDeleteResponse = storage.delete(request)
    def delete(request: StorageDeleteBulkRequest): StorageDeleteBulkResponse =
      storage.delete(request)

    def getLazyValue(request: LazyRequest): LazyResponse = `lazy`.get(request)

    def method1(request: ApiRequest1): ApiResponse1 = api.method1(request)

    def method2(request: ApiRequest2): ApiResponse2 = api.method2(request)
  }

  def apply(): App = {
    val greetingDao     = new GreetingDaoImpl
    val greetingService = new GreetingServiceImpl(greetingDao)
    new AppImpl(
      greetingService,
      new EchoServiceImpl(new EchoDaoImpl),
      new StorageServiceImpl(new StorageDaoImpl),
      new LazyServiceImpl(new LazyDaoImpl),
      new CounterDecoratorApiServiceImpl(new ApiServiceImpl, new CounterDaoImpl)
    )
  }
}
