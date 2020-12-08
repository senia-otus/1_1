package ru.otus.sc

import ru.otus.sc.counter.dao.impl.CounterDaoImpl
import ru.otus.sc.counter.model.CounterRequest.{NextValueRequest, PrevValueRequest, ResetCounterRequest}
import ru.otus.sc.counter.model._
import ru.otus.sc.counter.service.CounterService
import ru.otus.sc.counter.service.impl.CounterServiceImpl
import ru.otus.sc.currencyconvert.dao.impl.ConvertDaoImpl
import ru.otus.sc.currencyconvert.model.CurrencyType.{EUR, GBP, RUB, USD}
import ru.otus.sc.currencyconvert.model.{CurrencyConvertRequest, CurrencyConvertResponse}
import ru.otus.sc.currencyconvert.service.ConvertService
import ru.otus.sc.currencyconvert.service.impl.ConvertServiceImpl
import ru.otus.sc.echo.dao.impl.EchoDaoImpl
import ru.otus.sc.echo.model.{EchoRequest, EchoResponse}
import ru.otus.sc.echo.service.EchoService
import ru.otus.sc.echo.service.impl.EchoServiceImpl
import ru.otus.sc.greet.dao.impl.GreetingDaoImpl
import ru.otus.sc.greet.model.{GreetRequest, GreetResponse}
import ru.otus.sc.greet.service.GreetingService
import ru.otus.sc.greet.service.impl.GreetingServiceImpl
import ru.otus.sc.lazyfactorial.dao.impl.FactorialDaoImpl
import ru.otus.sc.lazyfactorial.model.{FactorialRequest, FactorialResponse}
import ru.otus.sc.lazyfactorial.service.FactorialService
import ru.otus.sc.lazyfactorial.service.impl.FactorialServiceImpl
import ru.otus.sc.storage.dao.impl.StorageDaoImpl
import ru.otus.sc.storage.model.{StorageRequest, StorageResponse}
import ru.otus.sc.storage.service.StorageService
import ru.otus.sc.storage.service.impl.StorageServiceImpl

trait App {
  def greet(request: GreetRequest): GreetResponse

  def echo(request: EchoRequest): EchoResponse

  def processCounter(request: CounterRequest): CounterResponse

  def storageGet(request: StorageRequest): StorageResponse
  def storageChange(request: StorageRequest): StorageResponse

  def calcFactorial(request: FactorialRequest): FactorialResponse

  def convertCurrency(request: CurrencyConvertRequest): CurrencyConvertResponse
}

object App {
  private class AppImpl(
      greeting: GreetingService,
      echoService: EchoService,
      counterService: CounterService,
      storageService: StorageService,
      factorialService: FactorialService,
      convertService: ConvertService
  ) extends App {

    def greet(request: GreetRequest): GreetResponse = greeting.greet(request)
    def echo(request: EchoRequest): EchoResponse    = echoService.echo(request)
    def processCounter(request: CounterRequest)     = counterService.processCounter(request)

    def storageGet(request: StorageRequest): StorageResponse    = storageService.get(request)
    def storageChange(request: StorageRequest): StorageResponse = storageService.change(request)

    def calcFactorial(request: FactorialRequest): FactorialResponse =
      factorialService.calcFactorial(request)

    def convertCurrency(request: CurrencyConvertRequest): CurrencyConvertResponse =
      convertService.convertCurrency(request)
  }

  def apply(): App = {
    val greetingDao     = new GreetingDaoImpl
    val greetingService = new GreetingServiceImpl(greetingDao)
    //--------------- ECHO SERVICE ---------------//
    val echoDao     = new EchoDaoImpl
    val echoService = new EchoServiceImpl(echoDao)
    //--------------- COUNTER SERVICE ------------//
    val counterDao     = new CounterDaoImpl
    val counterService = new CounterServiceImpl(counterDao)
    //--------------- STORAGE SERVICE ------------//
    val storageDao     = new StorageDaoImpl
    val storageService = new StorageServiceImpl(storageDao)
    //--------------- FACTORIAL SERVICE ----------//
    val factorialDao     = new FactorialDaoImpl
    val factorialService = new FactorialServiceImpl(factorialDao)
    //--------------- CONVERT CURRENCY SERVICE ---//
    val convertDao     = new ConvertDaoImpl
    val convertService = new ConvertServiceImpl(convertDao)

    new AppImpl(
      greetingService,
      echoService,
      counterService,
      storageService,
      factorialService,
      convertService
    )
  }

  def main(args: Array[String]): Unit = {
    val app = apply();
    println("//--------------- ECHO SERVICE ---------------//")
    val echo1 = app.echo(new EchoRequest("Hello, OTUS!", 1))
    val echo2 = app.echo(new EchoRequest("Hello, Scala!", 5))
    val echo3 = app.echo(new EchoRequest("wasssssap!", 0))
    val echo4 = app.echo(new EchoRequest("Will error", -10))
    println(s"result echo1 (just echo) = $echo1")
    println(s"result echo2 (with 5 repeats)= $echo2")
    println(s"result echo3 (with 0 repeats no echo) = $echo3")
    println(s"result echo4 (exception case) = $echo4")
    println("//-------------------------------------------//")
    println()
    println("//--------------- COUNTER SERVICE -----------//")
    val counter1 = app.processCounter(new NextValueRequest())
    val counter2 = app.processCounter(new NextValueRequest(4))
    val counter3 = app.processCounter(new PrevValueRequest(2))
    val counter4 = app.processCounter(new ResetCounterRequest())
    val counter5 = app.processCounter(new PrevValueRequest())
    println(s"result counter1 (next to one)= $counter1")
    println(s"result counter2 (next to 4 steps)= $counter2")
    println(s"result counter3 (prev to 2 steps)= $counter3")
    println(s"result counter4 (reset counter)= $counter4")
    println(s"result counter5 (exception case)= $counter5")
    println("//-------------------------------------------//")
    println()
    println("//--------------- STORAGE SERVICE -----------//")
    val storage1 = app.storageGet(StorageRequest("Russia"))
    val storage2 = app.storageChange(StorageRequest("Russia", "Rostov-on-Don"))
    val storage3 = app.storageGet(StorageRequest("Russia"))
    val storage4 = app.storageChange(StorageRequest("France", "Paris"))
    val storage5 = app.storageGet(StorageRequest("France"))
    println(s"result storage1 (get exist value)= $storage1")
    println(s"result storage2 (change value)= $storage2")
    println(s"result storage3 (get changed value)= $storage3")
    println(s"result storage4 (try change/add new record)= $storage4")
    println(s"result storage5 (try get new value)= $storage5")
    println("//-------------------------------------------//")
    println()
    println("//--------------- FACTORIAL SERVICE ---------//")
    val fact1 = app.calcFactorial(FactorialRequest(100, true))
    val fact2 = app.calcFactorial(FactorialRequest(100))
    val fact3 = app.calcFactorial(FactorialRequest(-50, true))
    println(s"result fact1 (with lazy calc) = $fact1")
    println(s"result fact2 (non lazy) = $fact2")
    println(s"result fact3 (exception case with lazy) = $fact3")
    println()
    println("//--------------- CONVERT CURRENCY SERVICE --//")
    val convertCurr1 = app.convertCurrency(CurrencyConvertRequest(10f, USD, RUB))
    val convertCurr2 = app.convertCurrency(CurrencyConvertRequest(743.2f, RUB, USD))
    val convertCurr3 = app.convertCurrency(CurrencyConvertRequest(10f, GBP, EUR))
    val convertCurr4 = app.convertCurrency(CurrencyConvertRequest(10f, EUR, EUR))
    println(s"result convertCurr1 (success convert) = $convertCurr1")
    println(s"result convertCurr2 (reverse convert) = $convertCurr2")
    println(s"result convertCurr3 (exception not found fromCurrency)= $convertCurr3")
    println(s"result convertCurr4 (exception not found toCurrency)= $convertCurr4")
  }
}
