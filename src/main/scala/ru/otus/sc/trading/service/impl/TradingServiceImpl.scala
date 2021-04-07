package ru.otus.sc.trading.service.impl

import ru.otus.sc.trading.dao.{MarketDao, WalletDao}
import ru.otus.sc.trading.model._
import ru.otus.sc.trading.service.TradingService

class TradingServiceImpl(market: MarketDao, wallet: WalletDao) extends TradingService {

  /**
    * Купить монету
    *
    * @param request запрос на покупку монеты
    * @return результат покупки
    */
  override def buyCoin(request: CoinBuyRequest): CoinBuyResponse = {
    val marketPrice = market.lastPrice(request.name)
    val buyCashSize = request.size * marketPrice
    val balanceSize = wallet.cashBalance()
    if (balanceSize > buyCashSize) {
      if (wallet.addCoins(request.name, marketPrice, request.size))
        CoinBuyResponseSuccess(request.name, marketPrice, request.size)
      else
        CoinBuyResponseFail(s"Coin [${request.name}] doesn't exists")
    } else {
      CoinBuyResponseFail(s"Not enough money. Need [${buyCashSize - balanceSize}]")
    }
  }

  /**
    * Продать монету
    *
    * @param request
    * @return
    */
  override def sellCoin(request: CoinSellRequest): CoinSellResponse = {
    val inWallet = wallet.coinBalance(request.name)
    if (inWallet >= request.size) {
      val marketPrice = market.lastPrice(request.name)
      val cashSize    = marketPrice * request.size
      wallet.removeCoins(request.name, request.size)
      wallet.addCash(cashSize)
      CoinSellResponseSuccess(request.name, request.size, marketPrice)
    } else {
      CoinSellResponseFail(s"Not enough size of coin in wallet. Need [${request.size - inWallet}]")
    }
  }

  /**
    * Получить список монет
    *
    * @return список доступных монет
    */
  override def listCoins: CoinListResponse = {
    CoinListResponse(market.coinList())
  }

  /**
    * Получить стоимость монеты
    *
    * @param request название монеты
    * @return стоимость
    */
  override def coinPrice(request: CoinPriceRequest): CoinPriceResponse = {
    if (market.exists(request.name)) {
      CoinPriceResponseSuccess(market.nextPrice(request.name))
    } else {
      CoinPriceResponseFail("Coin doesn't exists")
    }
  }

  /**
    * Получить баланс кошелька
    *
    * @return баланс денег
    */
  override def walletBalanceGet(): WalletBalanceGetResponse = {
    WalletBalanceGetResponse(wallet.cashBalance(), wallet.coinBalance())
  }

  /**
    * Пополнить баланс кошелька
    *
    * @param request запрос на пополнение кошелька
    * @return изменения баланса кошелька
    */
  override def walletBalancePut(request: WalletBalancePutRequest): WalletBalancePutResponse = {
    WalletBalancePutResponse(wallet.cashBalance(), wallet.addCash(request.cashSize))
  }
}
