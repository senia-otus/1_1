package ru.otus.sc.storage.model

/**
  * DTO-модель запроса поиска по ключу
  * @param key ключ
  */
case class StorageFindRequest(key: String)

/**
  * DTO-модель запроса поиска по ключам
  * @param keys ключи
  */
case class StorageFindBulkRequest(keys: List[String] = Nil)
