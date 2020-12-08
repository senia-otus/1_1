package ru.otus.sc.currencyconvert.model

import ru.otus.sc.currencyconvert.model.CurrencyType.CurrencyType

/**
  * Request for convert currency
  *
  * @param amount amount of money
  * @param from from which currency we convert
  * @param to to which currency we convert
  */
case class CurrencyConvertRequest(amount: Float, from: CurrencyType, to: CurrencyType)
