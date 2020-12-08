package ru.otus.sc.currencyconvert.service.impl

import ru.otus.sc.currencyconvert.dao.ConvertDao
import ru.otus.sc.currencyconvert.model.{CurrencyConvertRequest, CurrencyConvertResponse}
import ru.otus.sc.currencyconvert.service.ConvertService

/**
  * Implementation service for convertion from currency to another currency
  * @param dao convertion currency
  */
class ConvertServiceImpl(dao: ConvertDao) extends ConvertService {

  /**
    * Process convertion two currency
    * Сan be converted with more than 10 гтшеы
    * @param request request type
    * @return response type
    * @see CurrencyConvertRequest CurrencyConvertResponse
    */
  override def convertCurrency(request: CurrencyConvertRequest): CurrencyConvertResponse = {
    try {
      require(request.amount >= 10f)
      val resultConvert = dao.convert(request.amount, request.from, request.to)
      resultConvert match {
        case None =>
          CurrencyConvertResponse(
            -1,
            s"Not found one of currency from [${request.from}, ${request.to}]"
          ) // todo можно более информативно, но пока не проходили обработку ошибок
        case _ => CurrencyConvertResponse(resultConvert.get)
      }
    } catch {
      case e: NoSuchElementException =>
        CurrencyConvertResponse(
          -1,
          s"Not found one of currency from [${request.from}, ${request.to}]"
        ) // todo можно более информативно, но пока не проходили обработку ошибок
      case e: IllegalArgumentException =>
        CurrencyConvertResponse(
          -1,
          s"Invalid amount=[${request.amount}] for convert. Amount must be more 10 units."
        )
    }
  }
}
