package ru.otus.sc

import ru.otus.sc.Engine.{Greeted, StorageKey, StorageValue}
import ru.otus.sc.countdown.dao.impl.CountdownDaoImpl
import ru.otus.sc.countdown.service.CountdownService
import ru.otus.sc.countdown.service.impl.CountdownServiceImpl
import ru.otus.sc.counter.dao.impl.CounterDaoImpl
import ru.otus.sc.counter.service.CounterService
import ru.otus.sc.counter.service.impl.CounterServiceImpl
import ru.otus.sc.echo.dao.impl.EchoDaoImpl
import ru.otus.sc.echo.service.EchoService
import ru.otus.sc.echo.service.impl.EchoServiceImpl
import ru.otus.sc.greet.dao.impl.GreetingDaoImpl
import ru.otus.sc.greet.service.GreetingService
import ru.otus.sc.greet.service.impl.GreetingServiceImpl
import ru.otus.sc.reverse.dao.impl.ReverseDaoImpl
import ru.otus.sc.reverse.service.ReverseService
import ru.otus.sc.reverse.service.impl.ReverseServiceImpl
import ru.otus.sc.storage.dao.impl.StorageDaoImpl
import ru.otus.sc.storage.service.StorageService
import ru.otus.sc.storage.service.impl.StorageServiceImpl
import ru.otus.sc.sum.dao.impl.SumDaoImpl
import ru.otus.sc.sum.service.SumService
import ru.otus.sc.sum.service.impl.SumServiceImpl
import ru.otus.sc.user.dao.impl.{UserDaoImpl, UserTagDaoImpl}
import ru.otus.sc.user.model.User
import ru.otus.sc.user.service.impl.{UserServiceImpl, UserTagServiceImpl}
import ru.otus.sc.user.service.{UserService, UserTagService}

// Helper class which aggregates service entries to single point
case class Engine(
    countdowning: CountdownService,
    counting: CounterService,
    echoing: EchoService,
    getting: StorageService[StorageKey, StorageValue],
    greeting: GreetingService[Greeted],
    reversing: ReverseService,
    summing: SumService,
    usering: UserService,
    userTagging: UserTagService
)

object Engine {
  type StorageKey   = String
  type StorageValue = String
  type Greeted      = User

  def apply(): Engine = {
    val CountdownDao     = new CountdownDaoImpl
    val countdownService = new CountdownServiceImpl(CountdownDao)
    val CounterDao       = new CounterDaoImpl
    val counterService   = new CounterServiceImpl(CounterDao)
    val echoDao          = new EchoDaoImpl
    val echoService      = new EchoServiceImpl(echoDao)
    val greetingDao      = new GreetingDaoImpl
    val greetingService  = new GreetingServiceImpl[Greeted](greetingDao)
    val storageDao       = new StorageDaoImpl[StorageKey, StorageValue]
    val storageService   = new StorageServiceImpl[StorageKey, StorageValue](storageDao)
    val reverseDao       = new ReverseDaoImpl
    val reverseService   = new ReverseServiceImpl(reverseDao)
    val sumDao           = new SumDaoImpl
    val sumService       = new SumServiceImpl(sumDao)
    val userDao          = new UserDaoImpl
    val userService      = new UserServiceImpl(userDao)
    val userTagDao       = new UserTagDaoImpl
    val userTagService   = new UserTagServiceImpl(userTagDao)

    Engine(
      countdownService,
      counterService,
      echoService,
      storageService,
      greetingService,
      reverseService,
      sumService,
      userService,
      userTagService
    )
  }
}
