import ru.otus.sc._
import ru.otus.sc.trading.model._
val app = App()
app.walletBalanceGet()
app.listCoins
app.coinPrice(CoinPriceRequest("UNKNOWN"))
app.coinPrice(CoinPriceRequest("BTC"))
app.coinPrice(CoinPriceRequest("BTC"))
app.buyCoin(CoinBuyRequest("BTC", 0.03))
app.buyCoin(CoinBuyRequest("BTC", 0.00002))
app.walletBalanceGet()
