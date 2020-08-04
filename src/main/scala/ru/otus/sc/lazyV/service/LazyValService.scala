package ru.otus.sc.lazyV.service

import ru.otus.sc.lazyV.model.{LazyValCalledResponse, LazyValResponse}

/**
  * Service for Lazy Value
  */
trait LazyValService {

  /**
    * Get lazy val
    *
    * @return response with lazy val
    */
  def getLazyValue: LazyValResponse

  /**
    * Get flag isCalled for lazy val
    *
    * @return response with flag isCalled
    */
  def getIsCalled: LazyValCalledResponse
}
