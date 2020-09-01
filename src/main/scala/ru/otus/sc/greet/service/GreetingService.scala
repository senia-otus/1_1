package ru.otus.sc.greet.service

import ru.otus.sc.greet.model.{GreetRequest, GreetResponse}

trait GreetingService[T] {
  def greet(request: GreetRequest[T]): GreetResponse
}
