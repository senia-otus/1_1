package ru.otus.sc.trading.dao

object MarketDao {
  val COINS = Map(
    "BTC" -> 53000.0,
    "BTT" -> 0.1230,
    "ADA" -> 23.0
  )

  val tradeSteps = Map(
    "BTC" -> 0.004,
    "BTT" -> 0.002,
    "ADA" -> 0.3
  )
}

trait MarketDao {

  /**
    * Существует ли монета
    *
    * @param name название
    * @return да или нет
    */
  def exists(name: String): Boolean

  /**
    * Получить последнюю цену монеты
    *
    * @param name имя монеты
    * @return последняя цена монеты
    */
  def lastPrice(name: String): Double

  /**
    * Получить следующую цену монеты
    *
    * @param name имя монеты
    * @return следующая цена
    */
  def nextPrice(name: String): Double

  /**
    * Получить список торговых монет
    *
    * @return список монет
    */
  def coinList(): List[String]
}
