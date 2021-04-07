package ru.otus.sc.`lazy`.dao

import ru.otus.sc.`lazy`.model.LazyValue

/**
  * Доменный репозиторий для получения синглетона
  */
trait LazyDao {
  def singleton[A: LazyValue]: A
}
