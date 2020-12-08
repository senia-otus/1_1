package ru.otus.sc.storage.dao.impl

import ru.otus.sc.storage.dao.StorageDao

import scala.collection.immutable.HashMap

/**
  * Implementation trait for manage non-expandable storage
  * @see StorageDao
  */
class StorageDaoImpl extends StorageDao {

  // non-expandable storage with (Country -> City) records
  private var storage = HashMap(
    "Russia" -> "Moscow",
    "USA"    -> "Washington",
    "Canada" -> "Ottawa"
  )

  /**
    * Search and return record from storage
    * @param key key for search
    * @return pair if key exist else none
    */
  override def getItem(key: String): Option[(String, String)] = {
    val result = storage.get(key)

    result match {
      case None => None
      case _    => Option((key, result.get))
    }

  }

  /**
    * Search and change found entry
    * @param item pair (Country -> City) for change
    * @return changed pair if key exist else none
    */
  override def changeItem(item: (String, String)): Option[(String, String)] = {
    val key   = item._1
    val value = item._2

    val searchResult = getItem(key)

    searchResult match {
      case None => None
      case _ => {
        storage += (key -> value)
        Option((key, value))
      }
    }

  }
}
