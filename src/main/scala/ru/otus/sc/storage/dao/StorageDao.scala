package ru.otus.sc.storage.dao

/**
  * Trait for storage has get record method and change record method.
  * Cannot add new record if this record not exist in storage
  */
trait StorageDao {
  def getItem(key: String): Option[(String, String)]
  def changeItem(item: (String, String)): Option[(String, String)]
}
