package ru.otus.sc.lazyV.service.impl

import ru.otus.sc.lazyV.dao.LazyValDao
import ru.otus.sc.lazyV.model.{LazyValCalledResponse, LazyValResponse}
import ru.otus.sc.lazyV.service.LazyValService

/**
  * Service implementation
  *
  * @param dao DAO layer with contains lazy value
  */
class LazyValServiceImpl(dao: LazyValDao) extends LazyValService {

  /**
    * Get lazy val
    *
    * @return response with lazy val
    */
  override def getLazyValue: LazyValResponse =
    LazyValResponse(dao.getLazyVal)

  /**
    * Get flag isCalled for lazy val
    *
    * @return response with flag isCalled
    */
  override def getIsCalled: LazyValCalledResponse =
    LazyValCalledResponse(dao.isCalled)
}
