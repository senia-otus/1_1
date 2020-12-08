package ru.otus.sc.currencyconvert.model

/**
  * Response type for convert currency
  * @param convertResult conversion result
  * @param errorMessage handle exception text
  */
case class CurrencyConvertResponse(convertResult: Float, errorMessage: String = "")
