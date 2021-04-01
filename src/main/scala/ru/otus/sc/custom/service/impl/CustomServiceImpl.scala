package ru.otus.sc.custom.service.impl

import ru.otus.sc.custom.dao.CustomDao
import ru.otus.sc.custom.model._
import ru.otus.sc.custom.service.CustomService

class CustomServiceImpl(dao: CustomDao) extends CustomService {
  override def showCountMethods(): ShowCountMethodsResponse =
    ShowCountMethodsResponse(dao.showCountMethods())

  override def showCounts(request: ShowCountsRequest): ShowCountsResponse = {
    if (dao.existMethod(request.method))
      ShowCountsResponseSuccess(dao.showCounts(request.method))
    else
      ShowCountsResponseFail(s"Not found method [${request.method}] in counts")
  }

  override def incMethod(request: IncMethodRequest): IncMethodResponse = {
    IncMethodResponse(dao.incMethod(request.method))
  }

  override def echo(request: EchoRequest): EchoResponse =
    EchoResponse(request.toEcho)

  override def isLazyInit: TestLazyValResponse =
    TestLazyValResponse(dao.testLazyValue())

  override def tryLazy(): LazyValResponse =
    LazyValResponse(dao.lazyValue())
}
