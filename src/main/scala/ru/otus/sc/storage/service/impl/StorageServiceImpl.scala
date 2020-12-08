package ru.otus.sc.storage.service.impl

import ru.otus.sc.storage.dao.StorageDao
import ru.otus.sc.storage.model.{StorageRequest, StorageResponse}
import ru.otus.sc.storage.service.StorageService

/**
  * Implementation service for manage storage
  * @param dao dao with country -> city storage
  * @see StorageService
  */
class StorageServiceImpl(dao: StorageDao) extends StorageService {

  /**
    * Search and return record from storage or message about fail
    * @param request key for search in storage
    * @return storage response type
    */
  override def get(request: StorageRequest): StorageResponse = {
    val result = dao.getItem(request.key)

    result match {
      case None => StorageResponse(("", ""), s"Not found record with key=[${request.key}]")
      case _    => StorageResponse(result.get)
    }
  }

  /**
    * Search and change record from storage or message about fail
    * @param request key for search in storage
    * @return storage response type
    */
  override def change(request: StorageRequest): StorageResponse = {
    val result = dao.changeItem((request.key, request.value))

    result match {
      case None =>
        StorageResponse(
          ("", ""),
          s"Not found record with key=[${request.key}]. Cannot add this record to storage."
        )
      case _ => StorageResponse(result.get)
    }
  }
}
