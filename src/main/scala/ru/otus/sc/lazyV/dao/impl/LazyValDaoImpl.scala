package ru.otus.sc.lazyV.dao.impl

import ru.otus.sc.lazyV.dao.LazyValDao

/**
  * Implementation of the service.
  *
  * @param testValue initialize lazy value
  */
class LazyValDaoImpl(testValue: Any) extends LazyValDao {
  private var called: Boolean = false

  private lazy val value: Any = {
    println("Test message ininitialize")
    this.called = !this.called
    testValue
  }

  override def getLazyVal: Any = this.value

  /**
    * Get flag of first call
    *
    * @return called or not
    */
  override def isCalled: Boolean = this.called
}
