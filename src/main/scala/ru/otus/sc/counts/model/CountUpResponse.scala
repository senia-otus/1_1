package ru.otus.sc.counts.model

/**
  * Response with change value from and to
  *
  * @param from value before update
  * @param to value after update
  */
case class CountUpResponse(from: Int, to: Int)
