package ru.otus.sc.auth.exceptions

case class CredentialsNotUniqueException(message: String) extends RuntimeException(message)
