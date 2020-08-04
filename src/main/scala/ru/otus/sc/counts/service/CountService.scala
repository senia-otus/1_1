package ru.otus.sc.counts.service

import ru.otus.sc.counts.model._

/**
  * Service to check call counters
  */
trait CountService {

  /**
    * Increment call counter by name of method
    *
    * @param method name of method
    */
  def countUp(method: String): Unit

  /**
    * Increment call counter by request from console
    *
    * @param request request with method name
    * @return response with values from and to
    */
  def countUp(request: CountUpRequest): CountUpResponse

  /**
    * Get count of call counter by name
    *
    * @param request request with method name
    * @return response with count
    */
  def countOf(request: CountOfRequest): CountOfResponse

  /**
    * Get all call counters with count
    *
    * @return response with key-value map of call counter and his count
    */
  def counts(): CountsResponse
}
