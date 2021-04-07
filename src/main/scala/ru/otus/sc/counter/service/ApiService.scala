package ru.otus.sc.counter.service

import ru.otus.sc.counter.model.{ApiRequest1, ApiRequest2, ApiResponse1, ApiResponse2}

/**
  * Сервис api-методов с поодержкой счетчика
  */
trait ApiService {
  def method1(request: ApiRequest1): ApiResponse1
  def method2(request: ApiRequest2): ApiResponse2
}
