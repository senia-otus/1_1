package ru.otus.sc.storage.service

import ru.otus.sc.storage.model._

trait StorageService[K, V] {
  def createStorage(request: CreateStorageRequest[K, V]): CreateStorageResponse[K, V]
  def getStorage(request: GetStorageRequest[K, V]): GetStorageResponse[K, V]
  def deleteStorage(request: DeleteStorageRequest[K, V]): DeleteStorageResponse[K, V]
  def updateStorage(request: UpdateStorageRequest[K, V]): UpdateStorageResponse[K, V]
  def findStorages(request: FindStoragesRequest[K, V]): FindStoragesResponse[K, V]
}
