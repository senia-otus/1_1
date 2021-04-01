package ru.otus.sc.greet

import ru.otus.sc.greet.model._
import ru.otus.sc.greet.service.GreetingService

trait ExtGreetingService {
  val greeting: GreetingService

  def greet(request: GreetRequest): GreetResponse =
    greeting.greet(request)
}
