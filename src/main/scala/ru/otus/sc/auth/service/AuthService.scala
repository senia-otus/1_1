package ru.otus.sc.auth.service

import ru.otus.sc.auth.model.{AuthRequest, AuthResponse, RegisterRequest, RegisterResponse}

import scala.concurrent.Future

trait AuthService {

  def auth(request: AuthRequest): Future[AuthResponse]
  def register(request: RegisterRequest): Future[RegisterResponse]

}
