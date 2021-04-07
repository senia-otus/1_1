package ru.otus.sc.trading.dao.impl

import ru.otus.sc.trading.dao.{WalletDao, MarketDao}

class WalletDaoImpl extends WalletDao {
  private var personCashBalance: Double              = 100.0f
  private var personCoinBalance: Map[String, Double] = MarketDao.COINS.keys.map(_ -> 0.0).toMap
  private var historyBuyCoin: List[(String, Double)] = List()

  /**
    * Получить баланс наличностей в кошельке
    *
    * @return
    */
  override def cashBalance(): Double = {
    this.personCashBalance
  }

  /**
    * Добавление денег в кошелек
    *
    * @param cash количество денег
    * @return новый баланс денег
    */
  override def addCash(cash: Double): Double = {
    this.personCashBalance += cash
    this.personCashBalance
  }

  /**
    * Добавить новую монету
    *
    * @param name  название
    * @param price цена
    * @param size  количество
    * @return получилось или не получилось
    */
  override def addCoins(name: String, price: Double, size: Double): Boolean = {
    if (this.personCoinBalance.contains(name)) {
      this.personCoinBalance = this.personCoinBalance + (name -> size)
      this.historyBuyCoin = (name, price) :: this.historyBuyCoin
      true
    } else {
      false
    }
  }

  /**
    * Получить список монет в кошельке
    *
    * @return список монет с количествами
    */
  override def coinBalance(): Map[String, Double] = {
    this.personCoinBalance
  }

  /**
    * Получить количество определенной монеты
    *
    * @param name имя монеты
    * @return количество
    */
  override def coinBalance(name: String): Double = {
    this.personCoinBalance.getOrElse(name, -1.0)
  }

  /**
    * Убрать из кошелька монеты
    *
    * @param name название
    * @param size количество
    * @return получилось или нет
    */
  override def removeCoins(name: String, size: Double): Boolean = {
    if (this.personCoinBalance.contains(name)) {
      val sizeChanged = this.personCoinBalance(name) - size
      if (sizeChanged >= 0) {
        this.personCoinBalance = this.personCoinBalance - name
        this.personCoinBalance = this.personCoinBalance + (name -> sizeChanged)
        true
      } else {
        false
      }
    } else {
      false
    }
  }
}
