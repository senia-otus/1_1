package ru.otus.sc

import ru.otus.sc.custom.ExtCustomService
import ru.otus.sc.custom.dao.impl.CustomDaoImpl
import ru.otus.sc.custom.service.CustomService
import ru.otus.sc.custom.service.impl.CustomServiceImpl
import ru.otus.sc.greet.ExtGreetingService
import ru.otus.sc.greet.dao.impl.GreetingDaoImpl
import ru.otus.sc.greet.service.GreetingService
import ru.otus.sc.greet.service.impl.GreetingServiceImpl
import ru.otus.sc.trading.ExtTradingService
import ru.otus.sc.trading.dao.impl.{MarketDaoImpl, WalletDaoImpl}
import ru.otus.sc.trading.service.TradingService
import ru.otus.sc.trading.service.impl.TradingServiceImpl

trait App extends ExtGreetingService with ExtCustomService with ExtTradingService {}

object App {
  private class AppImpl(
      val greeting: GreetingService,
      val custom: CustomService,
      val tradingService: TradingService
  ) extends App {}

  def apply(): App = {
    val greetingDao     = new GreetingDaoImpl
    val greetingService = new GreetingServiceImpl(greetingDao)

    val customDao     = new CustomDaoImpl
    val customService = new CustomServiceImpl(customDao)

    val marketDao      = new MarketDaoImpl
    val walletDao      = new WalletDaoImpl
    val tradingService = new TradingServiceImpl(marketDao, walletDao)

    new AppImpl(greetingService, customService, tradingService)
  }
}
