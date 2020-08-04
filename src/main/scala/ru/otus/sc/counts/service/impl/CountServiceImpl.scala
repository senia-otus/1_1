package ru.otus.sc.counts.service.impl

import ru.otus.sc.counts.dao.CountsDao
import ru.otus.sc.counts.model._
import ru.otus.sc.counts.service.CountService

/**
  * Service Call Counter implementation
  *
  * @param countsDao DAO layer to store call counters
  */
class CountServiceImpl(countsDao: CountsDao) extends CountService {

  /**
    * Increment call counter by name of method
    *
    * @param method name of method
    */
  override def countUp(method: String): Unit = {
    countsDao.countUp(method)
  }

  /**
    * Increment call counter by request from console
    *
    * @param request request with method name
    * @return response with values from and to
    */
  override def countUp(request: CountUpRequest): CountUpResponse = {
    val from = countsDao.countOf(request.key)
    countsDao.countUp(request.key)
    CountUpResponse(from, countsDao.countOf(request.key))
  }

  /**
    * Get count of call counter by name
    *
    * @param request request with method name
    * @return response with count
    */
  override def countOf(request: CountOfRequest): CountOfResponse =
    CountOfResponse(countsDao.countOf(request.key))

  /**
    * Get all call counters with count
    *
    * @return response with key-value map of call counter and his count
    */
  override def counts(): CountsResponse = CountsResponse(countsDao.counts())
}
