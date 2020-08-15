package ru.otus.sc.auth.model

case class RegisterRequest(login: String, password: String, name: String, email: String)
