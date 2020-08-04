package ru.otus.sc.store.service

import ru.otus.sc.store.model.{
  StoreGetAllResponse,
  StoreGetKeysResponse,
  StoreGetRequest,
  StoreGetResponse
}

/**
  * Service for get stored data
  */
trait StoreService {

  /**
    * Get stored data by name
    *
    * @param request request with name to extract value from store
    * @return stored data or empty result
    */
  def storeGet(request: StoreGetRequest): StoreGetResponse

  /**
    * Get keys of stored data
    *
    * @return list of keys
    */
  def storeGetKeys(): StoreGetKeysResponse

  /**
    * Get full stored key-value map
    *
    * @return key-value map
    */
  def storeGetAll(): StoreGetAllResponse
}
