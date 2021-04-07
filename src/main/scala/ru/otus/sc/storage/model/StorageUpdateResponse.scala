package ru.otus.sc.storage.model

/**
  * DTO-модель ответа обновления по ключу
  * @param updated обновлен/не обновлен
  */
case class StorageUpdateResponse(updated: Boolean)

/**
  * DTO-модель ответ обновления по ключам
  * @param keys обновленные ключи
  */
case class StorageUpdateBulkResponse(keys: List[String])
