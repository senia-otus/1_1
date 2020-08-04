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
}
