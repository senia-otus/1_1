package ru.otus.sc.storage.model

/**
  * Request for storage service
  * @param key key in storage
  * @param value value in storage
  */
case class StorageRequest(key: String, value: String = "")
