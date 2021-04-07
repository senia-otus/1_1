package ru.otus.sc.counter.model

/**
  * DTO-модель ответа метода 1
  * @param message сообщение
  * @param counter счетчик вызовов
  */
case class ApiResponse1(message: String, counter: Long = 0)
