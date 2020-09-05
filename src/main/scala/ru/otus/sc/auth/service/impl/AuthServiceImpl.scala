package ru.otus.sc.auth.service.impl

import ru.otus.sc.auth.data.dao.AuthDao
import ru.otus.sc.auth.model.{AuthRequest, AuthResponse, RegisterRequest, RegisterResponse}
import ru.otus.sc.auth.service.AuthService
import ru.otus.sc.common.ImplicitHelpers

import scala.concurrent.Future

class AuthServiceImpl(dao: AuthDao) extends AuthService with ImplicitHelpers {

  override def auth(request: AuthRequest): Future[AuthResponse] =
    dao
      .auth(request.login, request.password)
      .map(t => AuthResponse(t.value))

  override def register(request: RegisterRequest): Future[RegisterResponse] =
    dao
      .register(
        request.login,
        request.password,
        request.name,
        request.email
      )
      .map { token => RegisterResponse(token) }

}
