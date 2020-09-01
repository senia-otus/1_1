package ru.otus.sc.storage.model

case class CreateStorageRequest[K, V](entry: StorageEntry[K, V])

trait CreateStorageResponse[K, V]
object CreateStorageResponse {
  case class Created[K, V](entry: StorageEntry[K, V]) extends CreateStorageResponse[K, V]
  case class ErrorKeyExists[K, V](key: K)             extends CreateStorageResponse[K, V]
}
