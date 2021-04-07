package ru.otus.sc.counter.model

/**
  * DTO-модель ответа метода 2
  * @param message сообщение
  * @param counter счетчик вызовов
  */
case class ApiResponse2(message: String, counter: Long = 0)
