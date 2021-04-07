package ru.otus.sc.storage.dao

import ru.otus.sc.storage.model.{StorageKey, StorageValue}

/**
  * Репозиторий для получения/обновления значений по ключам
  */
trait StorageDao {
  def get(key: StorageKey): Option[StorageValue]
  def get(keys: List[StorageKey]): List[StorageValue]
  def insertOrUpdate(key: StorageKey, value: StorageValue): Unit
  def insertOrUpdate(data: List[(StorageKey, StorageValue)]): Unit
  def delete(key: StorageKey): Boolean
  def delete(keys: List[StorageKey]): List[(StorageKey, Boolean)]
}
