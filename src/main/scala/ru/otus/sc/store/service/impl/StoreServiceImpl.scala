package ru.otus.sc.store.service.impl

import ru.otus.sc.store.dao.StoreDao
import ru.otus.sc.store.model._
import ru.otus.sc.store.service.StoreService

/**
  * Implementation StoreData service
  *
  * @param dao StoreData DAO layer
  */
class StoreServiceImpl(dao: StoreDao) extends StoreService {

  /**
    * Get stored data by name
    *
    * @param request request with name to extract value from store
    * @return stored data or empty result
    */
  override def storeGet(request: StoreGetRequest): StoreGetResponse = {
    StoreGetResponse(dao.valueOf(request.key))
  }

  /**
    * Get keys of stored data
    *
    * @return list of keys
    */
  override def storeGetKeys(): StoreGetKeysResponse = {
    StoreGetKeysResponse(dao.keys)
  }

  /**
    * Get full stored key-value map
    *
    * @return key-value map
    */
  override def storeGetAll(): StoreGetAllResponse = {
    StoreGetAllResponse(dao.all())
  }
}
