package ru.otus.sc.custom

import ru.otus.sc.custom.model._
import ru.otus.sc.custom.service.CustomService

/**
  * Расширение для главного приложения Сервисом ДЗ
  */
trait ExtCustomService {

  /**
    * Отнаследованный Сервис ДЗ
    */
  val custom: CustomService

  /**
    * Метод показывает какие методы участвуют в подсчете
    *
    * @return список подсчитываемых методов
    */
  def showCountMethods(): ShowCountMethodsResponse = {
    val out = custom.showCountMethods()
    custom.incMethod(IncMethodRequest("showCountMethods"))
    out
  }

  /**
    * Метод показывает количество вызовов определенного метода
    *
    * @param request запрашиваемый метод
    * @return количество вызовом метода
    */
  def showCounts(request: ShowCountsRequest): ShowCountsResponse = {
    val out = custom.showCounts(request)
    custom.incMethod(IncMethodRequest("showCounts"))
    out
  }

  /**
    * Метод выводит то, что ему передали
    *
    * @param request выражение для вывода
    * @return переданное выражение
    */
  def echo(request: EchoRequest): EchoResponse = {
    val out = custom.echo(request)
    custom.incMethod(IncMethodRequest("echo"))
    out
  }

  /**
    * Метод показывает вызывался ли lazy val
    *
    * @return lazy val был вызван или нет
    */
  def isLazyInit: TestLazyValResponse = {
    val out = custom.isLazyInit
    custom.incMethod(IncMethodRequest("isLazyInit"))
    out
  }

  /**
    * Метод выводит lazy val
    *
    * @return хранящийся lazy val
    */
  def tryLazy(): LazyValResponse = {
    val out = custom.tryLazy()
    custom.incMethod(IncMethodRequest("tryLazy"))
    out
  }
}
