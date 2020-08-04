package ru.otus.sc.store.model

import ru.otus.sc.utils.model.PrettyPrint

/**
  * Response to get full key-value map of StoreData
  *
  * @param info full key-value map
  */
case class StoreGetAllResponse(info: Map[String, Any]) extends PrettyPrint[String, Any] {
  override val prelude: String = "Call counter for methods:"
}
