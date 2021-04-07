package ru.otus.sc.storage.dao.impl

import ru.otus.sc.storage.dao.StorageDao
import ru.otus.sc.storage.model.{StorageKey, StorageValue}
import scala.collection.mutable

class StorageDaoImpl extends StorageDao {
  private val _storage: mutable.Map[StorageKey, StorageValue] = mutable.Map()

  def get(key: StorageKey): Option[StorageValue] = _storage.get(key)

  def get(keys: List[StorageKey]): List[StorageValue] =
    _storage.filter(k => keys.contains(k._1)).values.toList

  def insertOrUpdate(key: StorageKey, value: StorageValue): Unit =
    _storage.update(key, value)

  def insertOrUpdate(data: List[(StorageKey, StorageValue)]): Unit =
    data.foreach(d => insertOrUpdate(d._1, d._2))

  def delete(key: StorageKey): Boolean =
    _storage.remove(key).fold(false)(v => true)

  def delete(keys: List[StorageKey]): List[(StorageKey, Boolean)] = keys.map(k => (k, delete(k)))
}
