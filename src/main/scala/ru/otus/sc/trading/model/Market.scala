package ru.otus.sc.trading.model

trait Market {}
trait MarketResponse extends Market { def isFail: Boolean }

case class CoinBuyRequest(name: String, size: Double) extends Market

trait CoinBuyResponse extends MarketResponse {}
case class CoinBuyResponseSuccess(
    name: String,
    price: Double,
    size: Double,
    isFail: Boolean = false
) extends CoinBuyResponse {
  val cashSize = price * size
}
case class CoinBuyResponseFail(ex: String, isFail: Boolean = true) extends CoinBuyResponse

case class CoinSellRequest(name: String, size: Double) extends Market

trait CoinSellResponse extends MarketResponse {}
case class CoinSellResponseSuccess(
    name: String,
    size: Double,
    price: Double,
    isFail: Boolean = false
)                                                                   extends CoinSellResponse
case class CoinSellResponseFail(ex: String, isFail: Boolean = true) extends CoinSellResponse

case class CoinListResponse(coins: List[String]) extends Market

case class CoinPriceRequest(name: String) extends Market

trait CoinPriceResponse extends MarketResponse {}
case class CoinPriceResponseSuccess(price: Double, isFail: Boolean = false)
    extends CoinPriceResponse
case class CoinPriceResponseFail(ex: String, isFail: Boolean = true) extends CoinPriceResponse
