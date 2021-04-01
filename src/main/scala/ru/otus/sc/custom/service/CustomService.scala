package ru.otus.sc.custom.service

import ru.otus.sc.custom.model._

trait CustomService {
  def showCountMethods(): ShowCountMethodsResponse
  def showCounts(request: ShowCountsRequest): ShowCountsResponse
  def incMethod(request: IncMethodRequest): IncMethodResponse

  def echo(request: EchoRequest): EchoResponse

  def isLazyInit: TestLazyValResponse
  def tryLazy(): LazyValResponse
}
