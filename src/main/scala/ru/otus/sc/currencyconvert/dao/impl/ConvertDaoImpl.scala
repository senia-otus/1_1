package ru.otus.sc.currencyconvert.dao.impl

import ru.otus.sc.currencyconvert.dao.ConvertDao
import ru.otus.sc.currencyconvert.model.CurrencyType.{CurrencyType, EUR, RUB, USD}

import scala.collection.immutable.HashMap
import scala.math.BigDecimal.RoundingMode.HALF_UP

/**
  * Implementation convertion of currency.
  * Have rates for RUB, USD, EUR
  */
class ConvertDaoImpl extends ConvertDao {

  /** Exchange rates one amount currency to one amount another currency */
  final private val exchangeRates: Map[CurrencyType, Map[CurrencyType, Float]] = HashMap(
    RUB -> HashMap(EUR -> 0.011f, USD -> 0.0136f),
    USD -> HashMap(RUB -> 73.5414f, EUR -> 0.83f),
    EUR -> HashMap(USD -> 1.21f, RUB -> 90.00f)
  )

  /**
    * Process of convertion
    * @param amount amount of money for convertion
    * @param fromCurrency   currency from which to convert
    * @param toCurrency   currency ещ which to convert
    * @return conversion result or None
    */
  override def convert(
      amount: Float,
      fromCurrency: CurrencyType,
      toCurrency: CurrencyType
  ): Option[Float] = {

    val ratesFromCurrency = exchangeRates.get(fromCurrency)
    ratesFromCurrency match {
      case None => None
      case _ => {
        val rateFromCurrencyTo = Option(ratesFromCurrency.get(toCurrency))
        rateFromCurrencyTo match {
          case None => None
          case _    => Option(BigDecimal(amount * rateFromCurrencyTo.get).setScale(2, HALF_UP).toFloat)
        }
      }
    }
  }
}
