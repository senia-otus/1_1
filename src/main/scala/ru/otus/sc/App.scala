package ru.otus.sc

import ru.otus.sc.ahtung.dao.AhtungDao
import ru.otus.sc.ahtung.dao.impl.AhtungDaoImpl
import ru.otus.sc.ahtung.model.{AhtungRequest, Response}
import ru.otus.sc.ahtung.service.AhtungService
import ru.otus.sc.ahtung.service.impl.AhtungServiceImpl
import ru.otus.sc.greet.dao.impl.GreetingDaoImpl
import ru.otus.sc.greet.model.{GreetRequest, GreetResponse}
import ru.otus.sc.greet.service.GreetingService
import ru.otus.sc.greet.service.impl.GreetingServiceImpl

trait App {
  def greet(request: GreetRequest): GreetResponse
  def ahtung(request: AhtungRequest): Response
}

object App {
  private class AppImpl(greeting: GreetingService, ahtungService: AhtungService) extends App {
    def greet(request: GreetRequest): GreetResponse = greeting.greet(request)
    def ahtung(request: AhtungRequest): Response = ahtungService.ahtung(request)
  }

  def apply(): App = {
    val greetingDao     = new GreetingDaoImpl
    val ahtungDao       = new AhtungDaoImpl
    val greetingService = new GreetingServiceImpl(greetingDao)
    val ahtungService   = new AhtungServiceImpl(ahtungDao)

    new AppImpl(greetingService, ahtungService)
  }
}
