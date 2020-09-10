package ru.otus.sc

import ru.otus.sc.counts.dao.impl.CountsDaoImpl
import ru.otus.sc.counts.model._
import ru.otus.sc.counts.service.CountService
import ru.otus.sc.counts.service.impl.CountServiceImpl
import ru.otus.sc.echo.model.{EchoRequest, EchoResponse}
import ru.otus.sc.echo.service.EchoService
import ru.otus.sc.echo.service.impl.EchoServiceImpl
import ru.otus.sc.game.dao.impl.GameCredentialsDaoImpl
import ru.otus.sc.game.service.GameService
import ru.otus.sc.game.service.impl.GameServiceImpl
import ru.otus.sc.greet.dao.impl.GreetingDaoImpl
import ru.otus.sc.greet.model._
import ru.otus.sc.greet.service.GreetingService
import ru.otus.sc.greet.service.impl.GreetingServiceImpl
import ru.otus.sc.lazyV.dao.impl.LazyValDaoImpl
import ru.otus.sc.lazyV.model.{LazyValCalledResponse, LazyValResponse}
import ru.otus.sc.lazyV.service.LazyValService
import ru.otus.sc.lazyV.service.impl.LazyValServiceImpl
import ru.otus.sc.store.dao.impl.StoreDaoImpl
import ru.otus.sc.store.model._
import ru.otus.sc.store.service.StoreService
import ru.otus.sc.store.service.impl.StoreServiceImpl

trait App {

  /**
    * GreetService. Launch sample greet method.
    *
    * @param request request with name and check field is human
    * @return response greet to the name
    */
  def greet(request: GreetRequest): GreetResponse

  /**
    * EchoService. Launch 'echo' method
    *
    * @param request request with value to output
    * @return response for output
    */
  def echo(request: EchoRequest): EchoResponse

  /**
    * CountService. Launch method to increase custom key
    *
    * @param request request with custom key
    * @return response with values from and to
    */
  def customCountUp(request: CountUpRequest): CountUpResponse

  /**
    * CountService. Launch method to get size of the call counter
    *
    * @param request request with name of the call counter
    * @return response with size of the call counter
    */
  def countOf(request: CountOfRequest): CountOfResponse

  /**
    * CountService. Get all of call counters
    *
    * @return response with map of all call counters
    */
  def countsAll(): CountsResponse

  /**
    * StoreService. Get value of the element by the key
    *
    * @param request request with the key
    * @return response with value
    */
  def storeGet(request: StoreGetRequest): StoreGetResponse

  /**
    * StoreService. Get all available keys
    *
    * @return response with all available keys
    */
  def storeGetKeys(): StoreGetKeysResponse

  /**
    * StoreService. Get all available data
    *
    * @return response with all available data
    */
  def storeGetAll(): StoreGetAllResponse

  /**
    * LazyValService. Get lazy value from LazyValDao
    *
    * @return response with lazy value
    */
  def lazyValueGet(): LazyValResponse

  /**
    * LazyValService. Get flag isCalled
    *
    * @return response with flag isCalled
    */
  def lazyValueIsCaled(): LazyValCalledResponse

  def game: GameService
}

object App {
  private class AppImpl(
      greeting: GreetingService,
      counts: CountService,
      echo: EchoService,
      store: StoreService,
      lazyVal: LazyValService,
      game: GameService
  ) extends App {

    /**
      * GreetService. Launch sample greet method.
      *
      * @param request request with name and check field is human
      * @return response greet to the name
      */
    def greet(request: GreetRequest): GreetResponse = {
      counts.countUp("greet")
      greeting.greet(request)
    }

    /**
      * EchoService. Launch 'echo' method
      *
      * @param request request with value to output
      * @return response for output
      */
    def echo(request: EchoRequest): EchoResponse = {
      counts.countUp("echo")
      echo.echo(request)
    }

    /**
      * CountService. Launch method to increase custom key
      *
      * @param request request with custom key
      * @return response with values from and to
      */
    def customCountUp(request: CountUpRequest): CountUpResponse = counts.countUp(request)

    /**
      * CountService. Launch method to get size of the call counter
      *
      * @param request request with name of the call counter
      * @return response with size of the call counter
      */
    def countOf(request: CountOfRequest): CountOfResponse = counts.countOf(request)

    /**
      * CountService. Get all of call counters
      *
      * @return response with map of all call counters
      */
    def countsAll(): CountsResponse = counts.counts()

    /**
      * StoreService. Get value of the element by the key
      *
      * @param request request with the key
      * @return response with value
      */
    override def storeGet(request: StoreGetRequest): StoreGetResponse = {
      counts.countUp("storeGet")
      store.storeGet(request)
    }

    /**
      * StoreService. Get all available keys
      *
      * @return response with all available keys
      */
    override def storeGetKeys(): StoreGetKeysResponse = {
      counts.countUp("storeGetKeys")
      store.storeGetKeys()
    }

    /**
      * StoreService. Get all available data
      *
      * @return response with all available data
      */
    override def storeGetAll(): StoreGetAllResponse = {
      counts.countUp("storeGetAll")
      store.storeGetAll()
    }

    /**
      * LazyValService. Get lazy value from LazyValDao
      *
      * @return response with lazy value
      */
    override def lazyValueGet(): LazyValResponse = {
      counts.countUp("lazyValueGet")
      lazyVal.getLazyValue
    }

    /**
      * LazyValService. Get flag isCalled
      *
      * @return response with flag isCalled
      */
    override def lazyValueIsCaled(): LazyValCalledResponse = {
      counts.countUp("lazyValueIsCalled")
      lazyVal.getIsCalled
    }

    override def game(): GameService = game
  }

  def apply(): App = {
    // initialize all Dao
    val greetingDao = new GreetingDaoImpl
    val countsDao   = new CountsDaoImpl
    val storeDao    = new StoreDaoImpl(Map("a" -> "a", "b" -> 2, "c" -> false))
    val lazyValDao  = new LazyValDaoImpl(345)
    // initialize all Services
    val greetingService = new GreetingServiceImpl(greetingDao)
    val countService    = new CountServiceImpl(countsDao)
    val echoService     = new EchoServiceImpl()
    val storeService    = new StoreServiceImpl(storeDao)
    val lazyValService  = new LazyValServiceImpl(lazyValDao)
    // game service init
    val gameDao     = new GameCredentialsDaoImpl()
    val gameService = new GameServiceImpl(gameDao)
    // initilize App
    new AppImpl(
      greetingService,
      countService,
      echoService,
      storeService,
      lazyValService,
      gameService
    )

  }
}
