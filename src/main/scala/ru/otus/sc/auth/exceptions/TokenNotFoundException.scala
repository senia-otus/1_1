package ru.otus.sc.auth.exceptions

case class TokenNotFoundException(message: String) extends Exception(message)
