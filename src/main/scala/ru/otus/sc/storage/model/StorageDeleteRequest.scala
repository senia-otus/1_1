package ru.otus.sc.storage.model

/**
  * DTO-модель запроса одиночного удаления
  * @param key ключ
  */
case class StorageDeleteRequest(key: String)

/**
  * DTO-модель запроса множественного удаления
  * @param keys ключи
  */
case class StorageDeleteBulkRequest(keys: List[String])
