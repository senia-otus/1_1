package ru.otus.sc.utils.model

/**
  * Util trait to guarantee non empty key
  */
trait RequireKey {
  val key: String

  require(!key.isEmpty, "Field cannot be empty")
}
