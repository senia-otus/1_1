package ru.otus.sc.auth.service.impl

import ru.otus.sc.auth.dao.AuthDao
import ru.otus.sc.auth.model.{AuthRequest, AuthResponse, RegisterRequest, RegisterResponse}
import ru.otus.sc.auth.service.AuthService

class AuthServiceImpl(dao: AuthDao) extends AuthService {

  override def auth(request: AuthRequest): AuthResponse = dao.auth(request.login, request.password)
    .map(_.value)
    .map(AuthResponse)
    .getOrElse(AuthResponse(""))

  override def register(request: RegisterRequest): RegisterResponse = dao.register(
      request.login,
      request.password,
      request.name,
      request.email
    )
    .map(_.value)
    .map(RegisterResponse)
    .getOrElse(RegisterResponse(""))
}
