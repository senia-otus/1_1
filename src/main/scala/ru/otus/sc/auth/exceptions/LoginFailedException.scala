package ru.otus.sc.auth.exceptions

case class LoginFailedException(login: String) extends RuntimeException(s"login attempt failed with username -> $login")
