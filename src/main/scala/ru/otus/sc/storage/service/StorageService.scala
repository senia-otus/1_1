package ru.otus.sc.storage.service

import ru.otus.sc.storage.model.{StorageRequest, StorageResponse}

/**
 * Service for manage storage
 */
trait StorageService {
  def get(request: StorageRequest): StorageResponse
  def change(request: StorageRequest): StorageResponse
}
