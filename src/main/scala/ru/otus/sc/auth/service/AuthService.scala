package ru.otus.sc.auth.service

import ru.otus.sc.auth.model.{AuthRequest, AuthResponse, RegisterRequest, RegisterResponse, Token}

trait AuthService {

  def auth(request: AuthRequest): AuthResponse
  def register(request: RegisterRequest): RegisterResponse

}
