package ru.otus.sc.storage.service.impl

import ru.otus.sc.storage.dao.StorageDao
import ru.otus.sc.storage.model.{StorageRequest, StorageResponse}
import ru.otus.sc.storage.service.StorageService

class StorageServiceImpl(dao: StorageDao) extends StorageService {

  def get(request: StorageRequest): Option[StorageResponse] = {
    val value = dao.get(request.storageKey)
    // Can not use pattern matching or .map, so use if/else
    if (value.nonEmpty) Some(StorageResponse(value.get))
    else None
  }
}
