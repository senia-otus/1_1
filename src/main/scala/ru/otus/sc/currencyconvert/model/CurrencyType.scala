package ru.otus.sc.currencyconvert.model

/** Common type for currency */
case object CurrencyType extends Enumeration {
  type CurrencyType = Value
  val RUB, USD, EUR, GBP = Value
}

