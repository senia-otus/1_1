package ru.otus.sc.auth.model

case class User(name: String, login: String, email: String, password: String, token: Option[Token])
