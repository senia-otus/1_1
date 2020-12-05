package ru.otus.sc.greet.model

sealed trait ServiceError
case class NotFoundError(text: String) extends Exception(text) with ServiceError
