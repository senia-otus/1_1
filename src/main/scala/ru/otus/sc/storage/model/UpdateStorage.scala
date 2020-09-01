package ru.otus.sc.storage.model

case class UpdateStorageRequest[K, V](entry: StorageEntry[K, V])

trait UpdateStorageResponse[K, V]
object UpdateStorageResponse {
  case class Updated[K, V](entry: StorageEntry[K, V]) extends UpdateStorageResponse[K, V]
  case class NotFound[K, V](key: K)                   extends UpdateStorageResponse[K, V]
}
