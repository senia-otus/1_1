package ru.otus.sc.store.dao

/**
  * StoreData DAO layer
  */
trait StoreDao {

  /**
    * Get keys of StoreData
    *
    * @return list of keys
    */
  def keys: List[String]

  /**
    * Get value by key
    *
    * @param key key in StoreData
    * @return value or empty string
    */
  def valueOf(key: String): Any

  /**
    * Get full key-value map
    *
    * @return key-value map
    */
  def all(): Map[String, Any]
}
