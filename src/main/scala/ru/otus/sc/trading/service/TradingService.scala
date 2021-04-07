package ru.otus.sc.trading.service

import ru.otus.sc.trading.model._

trait TradingService {

  /**
    * Купить монету
    *
    * @param request запрос на покупку монеты
    * @return результат покупки
    */
  def buyCoin(request: CoinBuyRequest): CoinBuyResponse

  /**
    * Продать монету
    *
    * @param request
    * @return
    */
  def sellCoin(request: CoinSellRequest): CoinSellResponse

  /**
    * Получить список монет
    *
    * @return список доступных монет
    */
  def listCoins: CoinListResponse

  /**
    * Получить стоимость монеты
    *
    * @param request название монеты
    * @return стоимость
    */
  def coinPrice(request: CoinPriceRequest): CoinPriceResponse

  /**
    * Получить баланс кошелька
    *
    * @return баланс денег
    */
  def walletBalanceGet(): WalletBalanceGetResponse

  /**
    * Пополнить баланс кошелька
    *
    * @param request запрос на пополнение кошелька
    * @return изменения баланса кошелька
    */
  def walletBalancePut(request: WalletBalancePutRequest): WalletBalancePutResponse
}
