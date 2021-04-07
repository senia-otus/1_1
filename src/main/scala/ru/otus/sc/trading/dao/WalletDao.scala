package ru.otus.sc.trading.dao

trait WalletDao {

  /**
    * Получить баланс наличностей в кошельке
    * @return
    */
  def cashBalance(): Double

  /**
    * Добавление денег в кошелек
    *
    * @param cash количество денег
    * @return новый баланс денег
    */
  def addCash(cash: Double): Double

  /**
    * Добавить новую монету
    *
    * @param name название
    * @param price цена
    * @param size количество
    * @return получилось или не получилось
    */
  def addCoins(name: String, price: Double, size: Double): Boolean

  /**
    * Получить список монет в кошельке
    * @return список монет с количествами
    */
  def coinBalance(): Map[String, Double]

  /**
    * Получить количество определенной монеты
    *
    * @param name имя монеты
    * @return количество
    */
  def coinBalance(name: String): Double

  /**
    * Убрать из кошелька монеты
    *
    * @param name название
    * @param size количество
    * @return получилось или нет
    */
  def removeCoins(name: String, size: Double): Boolean
}
