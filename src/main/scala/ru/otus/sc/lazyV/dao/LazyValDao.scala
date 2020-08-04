package ru.otus.sc.lazyV.dao

/**
  * Service for get lazy value
  */
trait LazyValDao {

  /**
    * Get lazy value
    *
    * @return
    */
  def getLazyVal: Any

  /**
    * Get flag of first call
    * @return called or not
    */
  def isCalled: Boolean
}
