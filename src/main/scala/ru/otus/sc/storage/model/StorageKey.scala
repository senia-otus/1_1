package ru.otus.sc.storage.model

/**
  * ADT-тип для всех возможных вариантов ключей,
  * также реализован smart-конструктор в виде метода fromString
  */
sealed trait StorageKey {
  def value: String
}
object StorageKey {

  case object StorageKey1 extends StorageKey {
    val value: String = "Key1"
  }

  case object StorageKey2 extends StorageKey {
    val value: String = "Key2"
  }

  case object StorageKey3 extends StorageKey {
    val value: String = "Key3"
  }

  def fromString(key: String): Option[StorageKey] =
    if (validateKey(key)) allKeys.find(key == _.value)
    else None

  private def validateKey(key: String): Boolean = allKeys.exists(key == _.value)

  private lazy val allKeys: List[StorageKey] = StorageKey1 :: StorageKey2 :: StorageKey3 :: Nil
}
