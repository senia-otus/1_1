package ru.otus.sc.storage.dao

trait StorageDao {
  def get(key: String): Option[String]
}
