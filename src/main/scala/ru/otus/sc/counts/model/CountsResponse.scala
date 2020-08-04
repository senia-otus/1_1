package ru.otus.sc.counts.model

import ru.otus.sc.utils.model.PrettyPrint

/**
  * Class for store counts of launch methods
  *
  * @param info counts of launch methods separately
  */
case class CountsResponse(info: Map[String, Int]) extends PrettyPrint[String, Int] {
  override val prelude: String = "Call counter for methods:"
}
