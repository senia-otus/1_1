package ru.otus.sc.custom.dao.impl

import ru.otus.sc.custom.dao.CustomDao

class CustomDaoImpl extends CustomDao {

  // Заготовленный список методов для подсчитывания
  private var store = Map(
    "showCountMethods" -> 0,
    "showCounts"       -> 0,
    "echo"             -> 0,
    "isLazyInit"       -> 0,
    "tryLazy"          -> 0
  )

  // состояние lazy val
  private var isLazyInit: Boolean = false

  // сам lazy val
  private lazy val lazyVal: String = {
    this.isLazyInit = true
    "Lazy value initialized !"
  }

  /**
    * Метод показывает какие методы участвуют в подсчете
    *
    * @return список подсчитываемых методов
    */
  override def showCountMethods(): String = this.store.keys.mkString("\n")

  /**
    * Метод показывает количество вызовов определенного метода
    *
    * @param method запрашиваемый метод
    * @return количество вызовов метода или 0, если не существует
    */
  override def showCounts(method: String): Int = this.store.getOrElse(method, 0)

  /**
    * Метод проверяет существование переданного метода в списке для подсчитывания
    *
    * @param method запрашиваемый метод
    * @return количество вызовом метода
    */
  override def existMethod(method: String): Boolean = this.store.contains(method)

  /**
    * Метод увеличивает счетчик вызова переданного метода
    *
    * @param method вызванный метод
    * @return удалось или не удалось инкрементировать
    */
  override def incMethod(method: String): Boolean = {
    if (this.store.contains(method)) {
      this.store = this.store + (method -> (this.store(method) + 1))
      true
    } else {
      false
    }
  }

  /**
    * Метод выводит lazy val
    *
    * @return хранящийся lazy val
    */
  override def lazyValue(): String =
    this.lazyVal

  /**
    * Метод показывает вызывался ли lazy val
    *
    * @return lazy val был вызван или нет
    */
  override def testLazyValue(): Boolean =
    this.isLazyInit
}
