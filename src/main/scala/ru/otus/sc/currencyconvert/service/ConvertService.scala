package ru.otus.sc.currencyconvert.service

import ru.otus.sc.currencyconvert.model.{CurrencyConvertRequest, CurrencyConvertResponse}

/**
  * Service for convert currency
  */
trait ConvertService {
  def convertCurrency(request: CurrencyConvertRequest): CurrencyConvertResponse
}
