package ru.otus.sc.custom.dao

/**
  * Слой хранения для Сервиса ДЗ
  */
trait CustomDao {

  /**
    * Метод показывает какие методы участвуют в подсчете
    *
    * @return список подсчитываемых методов
    */
  def showCountMethods(): String

  /**
    * Метод показывает количество вызовов определенного метода
    *
    * @param method запрашиваемый метод
    * @return количество вызовов метода или 0, если не существует
    */
  def showCounts(method: String): Int

  /**
    * Метод проверяет существование переданного метода в списке для подсчитывания
    *
    * @param method запрашиваемый метод
    * @return количество вызовом метода
    */
  def existMethod(method: String): Boolean

  /**
    * Метод увеличивает счетчик вызова переданного метода
    *
    * @param method вызванный метод
    * @return удалось или не удалось инкрементировать
    */
  def incMethod(method: String): Boolean

  /**
    * Метод выводит lazy val
    *
    * @return хранящийся lazy val
    */
  def lazyValue(): String

  /**
    * Метод показывает вызывался ли lazy val
    *
    * @return lazy val был вызван или нет
    */
  def testLazyValue(): Boolean
}
