package ru.otus.sc.storage.model

case class GetStorageRequest[K, V](key: K)

trait GetStorageResponse[K, V]
object GetStorageResponse {
  case class Found[K, V](entry: StorageEntry[K, V]) extends GetStorageResponse[K, V]
  case class NotFound[K, V](key: K)                 extends GetStorageResponse[K, V]
}
