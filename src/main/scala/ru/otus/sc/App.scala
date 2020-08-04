package ru.otus.sc

import ru.otus.sc.country.dao.CountryDaoImpl
import ru.otus.sc.country.model._
import ru.otus.sc.country.service.{CountryService, CountryServiceImpl}
import ru.otus.sc.greet.dao.impl.GreetingDaoImpl
import ru.otus.sc.greet.model.{GreetRequest, GreetResponse}
import ru.otus.sc.greet.service.GreetingService
import ru.otus.sc.greet.service.impl.GreetingServiceImpl

trait App {
  def greet(request: GreetRequest): GreetResponse
  def country(request: CountryRequest): CountryResponse
}

object App {
  private class AppImpl(greeting: GreetingService, countryService: CountryService) extends App {
    def greet(request: GreetRequest): GreetResponse = greeting.greet(request)

    override def country(request: CountryRequest): CountryResponse =
      countryService.country(request)
  }

  def apply(): App = {
    val greetingDao     = new GreetingDaoImpl
    val greetingService = new GreetingServiceImpl(greetingDao)
    new AppImpl(greetingService, CountryServiceImpl(new CountryDaoImpl()))
  }
}
