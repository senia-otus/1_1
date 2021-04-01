package ru.otus.sc.custom.dao.impl

import ru.otus.sc.custom.dao.CustomDao

class CustomDaoImpl extends CustomDao {
  private var store = Map(
    "showCountMethods" -> 0,
    "showCounts"       -> 0,
    "echo"             -> 0,
    "isLazyInit"       -> 0,
    "tryLazy"          -> 0
  )

  private var isLazyInit: Boolean = false
  private lazy val lazyVal: String = {
    this.isLazyInit = true
    "Lazy value initialized !"
  }

  override def showCountMethods(): String = this.store.keys.mkString("\n")

  override def showCounts(method: String): Int = this.store.getOrElse(method, 0)

  override def existMethod(method: String): Boolean = this.store.contains(method)

  override def incMethod(method: String): Boolean = {
    if (this.store.contains(method)) {
      this.store = this.store + (method -> (this.store(method) + 1))
      true
    } else {
      false
    }
  }

  override def lazyValue(): String =
    this.lazyVal

  override def testLazyValue(): Boolean =
    this.isLazyInit
}
