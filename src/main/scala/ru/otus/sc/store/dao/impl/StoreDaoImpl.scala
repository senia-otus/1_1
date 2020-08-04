package ru.otus.sc.store.dao.impl

import ru.otus.sc.store.dao.StoreDao

/**
  * DAO Layer for StoreData with initialize values
  *
  * @param _store key-value map
  */
class StoreDaoImpl(private var _store: Map[String, Any] = Map()) extends StoreDao {

  /**
    * Get keys of StoreData
    *
    * @return list of keys
    */
  override def keys: List[String] = this._store.keys.toList

  /**
    * Get value by key
    *
    * @param key key in StoreData
    * @return value or empty string
    */
  override def valueOf(key: String): Any = this._store.getOrElse(key, "")

  /**
    * Get full key-value map
    *
    * @return key-value map
    */
  override def all(): Map[String, Any] = this._store
}
