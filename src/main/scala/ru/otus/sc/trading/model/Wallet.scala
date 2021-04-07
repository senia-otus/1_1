package ru.otus.sc.trading.model

trait Wallet {}
case class WalletBalanceGetResponse(cashBalace: Double, coinBalance: Map[String, Double])
    extends Wallet

case class WalletBalancePutRequest(cashSize: Double)          extends Wallet
case class WalletBalancePutResponse(from: Double, to: Double) extends Wallet

case class WalletCashWithdrawRequest()  extends Wallet
case class WalletCashWithdrawResponse() extends Wallet
