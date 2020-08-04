package ru.otus.sc.counts.dao

/**
  * DAO Layer interface of Call Counters
  */
trait CountsDao {

  /**
    * Increment call counter by name
    *
    * @param method name of call counter
    */
  def countUp(method: String): Unit

  /**
    * Get count of call counter by name
    *
    * @param method name of call counter
    * @return count of call counter
    */
  def countOf(method: String): Int

  /**
    * Get all Call Counters
    *
    * @return key-value map of all call counters
    */
  def counts(): Map[String, Int]
}
