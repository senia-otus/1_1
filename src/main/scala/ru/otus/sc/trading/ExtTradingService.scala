package ru.otus.sc.trading

import ru.otus.sc.trading.service.TradingService
import ru.otus.sc.trading.model._

trait ExtTradingService {
  val tradingService: TradingService

  def help(): String = {
    """
      |Доступные функции:
      |buyCoin - купить монету
      |sellCoin - продать монету
      |listCoins - список всех монет на рынке
      |coinPrice - текущая стоимость монеты
      |walletBalanceGet - баланс кошелька (кэш и монеты)
      |walletBalancePut - пополнение кошелька""".stripMargin
  }

  /**
    * Купить монету
    *
    * @param request запрос на покупку монеты
    * @return результат покупки
    */
  def buyCoin(request: CoinBuyRequest): CoinBuyResponse = {
    tradingService.buyCoin(request)
  }

  /**
    * Продать монету
    *
    * @param request
    * @return
    */
  def sellCoin(request: CoinSellRequest): CoinSellResponse = {
    tradingService.sellCoin(request)
  }

  /**
    * Получить список монет
    *
    * @return список доступных монет
    */
  def listCoins: CoinListResponse = {
    tradingService.listCoins
  }

  /**
    * Получить стоимость монеты
    *
    * @param request название монеты
    * @return стоимость
    */
  def coinPrice(request: CoinPriceRequest): CoinPriceResponse = {
    tradingService.coinPrice(request)
  }

  /**
    * Получить баланс кошелька
    *
    * @return баланс денег
    */
  def walletBalanceGet(): WalletBalanceGetResponse = {
    tradingService.walletBalanceGet()
  }

  /**
    * Пополнить баланс кошелька
    *
    * @param request запрос на пополнение кошелька
    * @return изменения баланса кошелька
    */
  def walletBalancePut(request: WalletBalancePutRequest): WalletBalancePutResponse = {
    tradingService.walletBalancePut(request)
  }
}
