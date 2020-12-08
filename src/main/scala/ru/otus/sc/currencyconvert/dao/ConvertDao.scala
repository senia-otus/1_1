package ru.otus.sc.currencyconvert.dao

import ru.otus.sc.currencyconvert.model.CurrencyType.CurrencyType

/**
  * convertion from one currency to another
  */
trait ConvertDao {
  def convert(amount: Float, fromCurrency: CurrencyType, toCurrency: CurrencyType): Option[Float]
}
