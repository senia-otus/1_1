package ru.otus.sc.storage.model

/**
  * DTO-модель запроса обновления по ключу
  * @param key ключ
  * @param value новое значение
  */
case class StorageUpdateRequest(key: String, value: String)

/**
  * DTO-модель запроса обновления по ключам
  * @param data данные в виде (ключ, значение)
  */
case class StorageUpdateBulkRequest(data: List[(String, String)])
