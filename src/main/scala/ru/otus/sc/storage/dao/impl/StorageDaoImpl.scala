package ru.otus.sc.storage.dao.impl

import ru.otus.sc.storage.dao.StorageDao

class StorageDaoImpl extends StorageDao {
  // Can not use collections, so use if/else
  def get(key: String): Option[String] = {
    if (key == "one") Some("1")
    else if (key == "two") Some("2")
    else if (key == "three") Some("3")
    else None
  }
}
