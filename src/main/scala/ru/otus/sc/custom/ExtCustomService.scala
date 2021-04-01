package ru.otus.sc.custom

import ru.otus.sc.custom.model._
import ru.otus.sc.custom.service.CustomService

trait ExtCustomService {
  val custom: CustomService

  def showCountMethods(): ShowCountMethodsResponse = {
    val out = custom.showCountMethods()
    custom.incMethod(IncMethodRequest("showCountMethods"))
    out
  }

  def showCounts(request: ShowCountsRequest): ShowCountsResponse = {
    val out = custom.showCounts(request)
    custom.incMethod(IncMethodRequest("showCounts"))
    out
  }

  def echo(request: EchoRequest): EchoResponse = {
    val out = custom.echo(request)
    custom.incMethod(IncMethodRequest("echo"))
    out
  }

  def isLazyInit: TestLazyValResponse = {
    val out = custom.isLazyInit
    custom.incMethod(IncMethodRequest("isLazyInit"))
    out
  }

  def tryLazy(): LazyValResponse = {
    val out = custom.tryLazy()
    custom.incMethod(IncMethodRequest("tryLazy"))
    out
  }
}
