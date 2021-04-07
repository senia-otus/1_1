package ru.otus.sc.counter.service.impl

import ru.otus.sc.counter.model.{ApiRequest1, ApiRequest2, ApiResponse1, ApiResponse2}
import ru.otus.sc.counter.service.ApiService

class ApiServiceImpl extends ApiService {
  def method1(request: ApiRequest1): ApiResponse1 = ApiResponse1(request.message)

  def method2(request: ApiRequest2): ApiResponse2 = ApiResponse2(request.message)
}
