package ru.otus.sc.storage.model

/**
  * DTO-модель ответа поиска по ключу
  * @param exists найден/ не найден
  * @param value значение
  */
case class StorageFindResponse(exists: Boolean, value: String = "")

/**
  * DTO-модель ответа поиска по ключам
  * @param values найденные значения
  */
case class StorageFindBulkResponse(values: List[(String, String)] = Nil)
