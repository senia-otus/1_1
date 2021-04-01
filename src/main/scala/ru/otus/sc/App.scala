package ru.otus.sc

import ru.otus.sc.custom.ExtCustomService
import ru.otus.sc.custom.dao.impl.CustomDaoImpl
import ru.otus.sc.custom.service.CustomService
import ru.otus.sc.custom.service.impl.CustomServiceImpl
import ru.otus.sc.greet.ExtGreetingService
import ru.otus.sc.greet.dao.impl.GreetingDaoImpl
import ru.otus.sc.greet.service.GreetingService
import ru.otus.sc.greet.service.impl.GreetingServiceImpl

trait App extends ExtGreetingService with ExtCustomService {}

object App {
  private class AppImpl(val greeting: GreetingService, val custom: CustomService) extends App {}

  def apply(): App = {
    val greetingDao     = new GreetingDaoImpl
    val greetingService = new GreetingServiceImpl(greetingDao)

    val customDao     = new CustomDaoImpl
    val customService = new CustomServiceImpl(customDao)

    new AppImpl(greetingService, customService)
  }
}
