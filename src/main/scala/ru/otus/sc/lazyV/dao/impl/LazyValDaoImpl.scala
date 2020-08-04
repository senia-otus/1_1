package ru.otus.sc.lazyV.dao.impl

import ru.otus.sc.lazyV.dao.LazyValDao

/**
  * Implementation of the service.
  *
  * @param testValue initialize lazy value
  */
class LazyValDaoImpl(testValue: Any) extends LazyValDao {
  private lazy val value: Any = {
    println("Test message ininitialize")
    testValue
  }

  override def getLazyVal: Any = this.value
}
