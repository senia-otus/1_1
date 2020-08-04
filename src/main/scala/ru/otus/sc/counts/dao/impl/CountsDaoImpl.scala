package ru.otus.sc.counts.dao.impl

import ru.otus.sc.counts.dao.CountsDao

/**
  * DAO Layer Implementation of Call Counters
  */
class CountsDaoImpl extends CountsDao {

  /**
    * Internal storage of call counters
    */
  private var _counts: Map[String, Int] = Map()

  /**
    * Increment call counter by name
    *
    * @param method name of call counter
    */
  override def countUp(method: String): Unit = {
    this._counts = this._counts + ((method, this._counts.getOrElse(method, 0) + 1))
  }

  /**
    * Get count of call counter by name
    *
    * @param method name of call counter
    * @return count of call counter
    */
  override def countOf(method: String): Int =
    this._counts.getOrElse(method, 0)

  /**
    * Get all Call Counters
    *
    * @return key-value map of all call counters
    */
  override def counts(): Map[String, Int] =
    this._counts
}
