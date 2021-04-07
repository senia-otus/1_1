package ru.otus.sc.storage.service

import ru.otus.sc.storage.model.{
  StorageDeleteBulkRequest,
  StorageDeleteBulkResponse,
  StorageDeleteRequest,
  StorageDeleteResponse,
  StorageFindBulkRequest,
  StorageFindBulkResponse,
  StorageFindRequest,
  StorageFindResponse,
  StorageKey,
  StorageUpdateBulkRequest,
  StorageUpdateBulkResponse,
  StorageUpdateRequest,
  StorageUpdateResponse,
  StorageValue
}

/**
  * Сервис поиска/обновления значений в хранилище по заранее заданному списку ключей
  * Реализованы методы для одиночного и множествественного поиска/обновления
  */
trait StorageService {
  def findBy(request: StorageFindRequest): StorageFindResponse
  def findBy(request: StorageFindBulkRequest): StorageFindBulkResponse
  def update(request: StorageUpdateRequest): StorageUpdateResponse
  def updateBulk(request: StorageUpdateBulkRequest): StorageUpdateBulkResponse
  def delete(request: StorageDeleteRequest): StorageDeleteResponse
  def delete(request: StorageDeleteBulkRequest): StorageDeleteBulkResponse
}
