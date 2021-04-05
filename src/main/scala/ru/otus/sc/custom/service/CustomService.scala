package ru.otus.sc.custom.service

import ru.otus.sc.custom.model._

/**
  * Интерфейс Сервиса ДЗ
  */
trait CustomService {

  /**
    * Метод показывает какие методы участвуют в подсчете
    *
    * @return список подсчитываемых методов
    */
  def showCountMethods(): ShowCountMethodsResponse

  /**
    * Метод показывает количество вызовов определенного метода
    *
    * @param request запрашиваемый метод
    * @return количество вызовов метода или Fail, если не существует
    */
  def showCounts(request: ShowCountsRequest): ShowCountsResponse

  /**
    * Метод увеличивает счетчик вызова переданного метода
    *
    * @param request вызванный метод
    * @return удалось или не удалось инкрементировать
    */
  def incMethod(request: IncMethodRequest): IncMethodResponse

  /**
    * Метод выводит то, что ему передали
    *
    * @param request выражение для вывода
    * @return переданное выражение
    */
  def echo(request: EchoRequest): EchoResponse

  /**
    * Метод показывает вызывался ли lazy val
    *
    * @return lazy val был вызван или нет
    */
  def isLazyInit: TestLazyValResponse

  /**
    * Метод выводит lazy val
    *
    * @return хранящийся lazy val
    */
  def tryLazy(): LazyValResponse
}
