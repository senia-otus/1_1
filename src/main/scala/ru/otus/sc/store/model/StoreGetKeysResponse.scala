package ru.otus.sc.store.model

/**
  * Response to get list of key of StoreData
  *
  * @param keys list of keys
  */
case class StoreGetKeysResponse(keys: List[String])
