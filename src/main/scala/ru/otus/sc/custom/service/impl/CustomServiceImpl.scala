package ru.otus.sc.custom.service.impl

import ru.otus.sc.custom.dao.CustomDao
import ru.otus.sc.custom.model._
import ru.otus.sc.custom.service.CustomService

class CustomServiceImpl(dao: CustomDao) extends CustomService {

  /**
    * Метод показывает какие методы участвуют в подсчете
    *
    * @return список подсчитываемых методов
    */
  override def showCountMethods(): ShowCountMethodsResponse =
    ShowCountMethodsResponse(dao.showCountMethods())

  /**
    * Метод показывает количество вызовов определенного метода
    *
    * @param request запрашиваемый метод
    * @return количество вызовов метода или Fail, если не существует
    */
  override def showCounts(request: ShowCountsRequest): ShowCountsResponse = {
    if (dao.existMethod(request.method))
      ShowCountsResponseSuccess(dao.showCounts(request.method))
    else
      ShowCountsResponseFail(s"Not found method [${request.method}] in counts")
  }

  /**
    * Метод увеличивает счетчик вызова переданного метода
    *
    * @param request вызванный метод
    * @return удалось или не удалось инкрементировать
    */
  override def incMethod(request: IncMethodRequest): IncMethodResponse = {
    IncMethodResponse(dao.incMethod(request.method))
  }

  /**
    * Метод выводит то, что ему передали
    *
    * @param request выражение для вывода
    * @return переданное выражение
    */
  override def echo(request: EchoRequest): EchoResponse =
    EchoResponse(request.toEcho)

  /**
    * Метод показывает вызывался ли lazy val
    *
    * @return lazy val был вызван или нет
    */
  override def isLazyInit: TestLazyValResponse =
    TestLazyValResponse(dao.testLazyValue())

  /**
    * Метод выводит lazy val
    *
    * @return хранящийся lazy val
    */
  override def tryLazy(): LazyValResponse =
    LazyValResponse(dao.lazyValue())
}
