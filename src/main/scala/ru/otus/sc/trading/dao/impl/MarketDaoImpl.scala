package ru.otus.sc.trading.dao.impl

import scala.util.{Random => rand}

import ru.otus.sc.trading.dao.MarketDao

class MarketDaoImpl extends MarketDao {
  private var coins: Map[String, Double] = MarketDao.COINS

  /**
    * Существует ли монета
    *
    * @param name название
    * @return да или нет
    */
  override def exists(name: String): Boolean = {
    coins.contains(name)
  }

  /**
    * Получить последнюю цену монеты
    *
    * @param name имя монеты
    * @return последняя цена монеты
    */
  override def lastPrice(name: String): Double = {
    this.coins.getOrElse(name, -1.0f)
  }

  /**
    * Получить следующую цену монеты
    *
    * @param name имя монеты
    * @return следующая цена
    */
  override def nextPrice(name: String): Double = {
    if (this.coins.contains(name)) {
      val linePrice: Int  = if (rand.nextInt(100) > 49) -1 else 1
      val step: Double    = MarketDao.tradeSteps(name) * (rand.nextInt(500) + 1) * linePrice
      val oldVal: Double  = this.coins(name)
      val nextVal: Double = oldVal + step
      this.coins = this.coins - name
      this.coins = this.coins + (name -> nextVal)
      this.coins(name)
    } else {
      -1.0f
    }
  }

  /**
    * Получить список торговых монет
    *
    * @return список монет
    */
  override def coinList(): List[String] = {
    this.coins.keys.toList
  }
}
