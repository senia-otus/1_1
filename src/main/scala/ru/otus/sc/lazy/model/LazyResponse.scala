package ru.otus.sc.`lazy`.model

/**
  * DTO-медель ответа для получениясинглетона
  * @param value значение
  * @param exists существует/не существует
  */
case class LazyResponse(value: Any, exists: Boolean = true)
