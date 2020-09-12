package ru.otus.sc

import ru.otus.sc.countdown.dao.impl.CountdownDaoImpl
import ru.otus.sc.countdown.model.{CountdownRequest, CountdownResponse}
import ru.otus.sc.countdown.service.CountdownService
import ru.otus.sc.countdown.service.impl.CountdownServiceImpl
import ru.otus.sc.counter.dao.impl.CounterDaoImpl
import ru.otus.sc.counter.model.{CounterRequest, CounterResponse}
import ru.otus.sc.counter.service.CounterService
import ru.otus.sc.counter.service.impl.CounterServiceImpl
import ru.otus.sc.echo.dao.impl.EchoDaoImpl
import ru.otus.sc.echo.model.{EchoRequest, EchoResponse}
import ru.otus.sc.echo.service.EchoService
import ru.otus.sc.echo.service.impl.EchoServiceImpl
import ru.otus.sc.greet.dao.impl.GreetingDaoImpl
import ru.otus.sc.greet.model.{GreetRequest, GreetResponse}
import ru.otus.sc.greet.service.GreetingService
import ru.otus.sc.greet.service.impl.GreetingServiceImpl
import ru.otus.sc.reverse.dao.impl.ReverseDaoImpl
import ru.otus.sc.reverse.model.{ReverseRequest, ReverseResponse}
import ru.otus.sc.reverse.service.ReverseService
import ru.otus.sc.reverse.service.impl.ReverseServiceImpl
import ru.otus.sc.storage.dao.impl.StorageDaoImpl
import ru.otus.sc.storage.model.{StorageRequest, StorageResponse}
import ru.otus.sc.storage.service.StorageService
import ru.otus.sc.storage.service.impl.StorageServiceImpl
import ru.otus.sc.sum.dao.impl.SumDaoImpl
import ru.otus.sc.sum.model.{SumRequest, SumResponse}
import ru.otus.sc.sum.service.SumService
import ru.otus.sc.sum.service.impl.SumServiceImpl

// Helper class which aggregates service entries to single point
case class Config(
    countdowning: CountdownService,
    counting: CounterService,
    echoing: EchoService,
    getting: StorageService,
    greeting: GreetingService,
    reversing: ReverseService,
    summing: SumService
)

object Config {
  def apply(): Config = {
    val CountdownDao     = new CountdownDaoImpl
    val countdownService = new CountdownServiceImpl(CountdownDao)
    val CounterDao       = new CounterDaoImpl
    val counterService   = new CounterServiceImpl(CounterDao)
    val echoDao          = new EchoDaoImpl
    val echoService      = new EchoServiceImpl(echoDao)
    val greetingDao      = new GreetingDaoImpl
    val greetingService  = new GreetingServiceImpl(greetingDao)
    val storageDao       = new StorageDaoImpl
    val storageService   = new StorageServiceImpl(storageDao)
    val reverseDao       = new ReverseDaoImpl
    val reverseService   = new ReverseServiceImpl(reverseDao)
    val sumDao           = new SumDaoImpl
    val sumService       = new SumServiceImpl(sumDao)

    Config(
      countdownService,
      counterService,
      echoService,
      storageService,
      greetingService,
      reverseService,
      sumService
    )
  }
}

trait App {
  // provide counter started from 1 with auto-increase value
  // every call increases counter value by 1
  // counter can be reset to initial value with `clear` flag
  def getCount(request: CounterRequest): CounterResponse
  // provide countdown started from `initValue` with auto-decrease value
  // has to be initiated with `CountdownClearRequest(initValue)`
  // otherwise it has "Done" state by default
  // when countdown reaches 0 it stops decrease value and returns 0
  // can be reset with `CountdownClearRequest(initValue)`
  // `initValue` less than 2 leads to set the countdown to "Done" state
  // default `initValue` is 1
  def countdown(request: CountdownRequest): CountdownResponse
  // reply on requested value with the same value
  // echo value can be multiplied up to 5 times with `repeatNum` value
  // no multiply answer by default (`repeatNum` is 1)
  // proper values return answer with `EchoAnswerResponse`
  // bad values return error with `EchoErrorResponse`
  def echo(request: EchoRequest): EchoResponse
  // provide value by requested key
  // in current implementation keys are one, two, three
  def get(request: StorageRequest): Option[StorageResponse]
  // greet with provided name value
  // panic if `isHuman` flag is unset
  def greet(request: GreetRequest): GreetResponse
  // reverse given string from end to begin
  def reverse(request: ReverseRequest): ReverseResponse
  // sum 2 given values locally
  // sum can be performed externally with `external` flag
  // in current implementation external computation is artificial delay
  // which was implemented with lazy value
  def sum(request: SumRequest): SumResponse
}

object App {
  def apply(): App = new AppImpl(Config())

  private class AppImpl(config: Config) extends App {
    def countdown(request: CountdownRequest): CountdownResponse =
      config.countdowning.countdown(request)
    def getCount(request: CounterRequest): CounterResponse =
      config.counting.getCount(request)
    def echo(request: EchoRequest): EchoResponse = config.echoing.echo(request)
    def get(request: StorageRequest): Option[StorageResponse] =
      config.getting.get(request)
    def greet(request: GreetRequest): GreetResponse =
      config.greeting.greet(request)
    def reverse(request: ReverseRequest): ReverseResponse =
      config.reversing.reverse(request)
    def sum(request: SumRequest): SumResponse = config.summing.sum(request)
  }
}
