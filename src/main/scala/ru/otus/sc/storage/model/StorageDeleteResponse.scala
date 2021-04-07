package ru.otus.sc.storage.model

/**
  * DTO-модель ответа одиночного удаления
  * @param deleted удален/не удален
  */
case class StorageDeleteResponse(deleted: Boolean)

/**
  * DTO-модель ответа множественного удаления
  * @param keys удаленные ключи
  */
case class StorageDeleteBulkResponse(keys: List[String])
