package ru.otus.sc.greet.service

import ru.otus.sc.greet.model._

trait GreetingService {

  def greet(request: GreetRequest): ServiceResponse

  def echo(request: EchoRequest): ServiceResponse

  def exit: ServiceResponse

  def getValue(request: GetValueRequest): ServiceResponse

  def counter: ServiceResponse

}
