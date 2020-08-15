package ru.otus.sc.storage.service

import ru.otus.sc.storage.model.{StorageRequest, StorageResponse}

trait StorageService {
  def get(request: StorageRequest): Option[StorageResponse]
}
