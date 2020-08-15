package ru.otus.sc

import ru.otus.sc.auth.dao.impl.AuthDaoImpl
import ru.otus.sc.auth.model.{AuthRequest, AuthResponse, RegisterRequest, RegisterResponse}
import ru.otus.sc.auth.service.AuthService
import ru.otus.sc.auth.service.impl.AuthServiceImpl
import ru.otus.sc.greet.dao.impl.GreetingDaoImpl
import ru.otus.sc.greet.model.{GreetRequest, GreetResponse}
import ru.otus.sc.greet.service.GreetingService
import ru.otus.sc.greet.service.impl.GreetingServiceImpl

trait App {
  def greet(request: GreetRequest): GreetResponse
  def auth(request: AuthRequest): AuthResponse
  def register(request: RegisterRequest): RegisterResponse
}

object App {
  private class AppImpl(greeting: GreetingService, authService: AuthService) extends App {
    override def greet(request: GreetRequest): GreetResponse = greeting.greet(request)
    override def auth(request: AuthRequest): AuthResponse = authService.auth(request)
    override def register(request: RegisterRequest): RegisterResponse = authService.register(request)
  }

  def apply(): App = {
    val greetingDao     = new GreetingDaoImpl  
    val greetingService = new GreetingServiceImpl(greetingDao)
    val authDao         = new AuthDaoImpl
    val authService     = new AuthServiceImpl(authDao)

    new AppImpl(greetingService, authService)
  }
}
