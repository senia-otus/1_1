package ru.otus.sc

import cats.effect.{ ConcurrentEffect, ContextShift, IO, Timer }
import distage.DIKey
import izumi.distage.config.ConfigModuleDef
import izumi.distage.plugins.{ PluginConfig, PluginDef }
import izumi.distage.testkit.scalatest.{ AssertCIO, Spec1 }
import org.http4s.HttpRoutes
import org.http4s.client.Client
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.implicits.http4sKleisliResponseSyntaxOptionT
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.{ Router, Server }
import org.scalatest.matchers.should.Matchers
import ru.otus.sc.greet.dao.{ GreetingDao, UserDao }
import ru.otus.sc.greet.service.{ BotService, GreetingService, UserService }
import ru.otus.sc.greet.{ Config, GreetingClient, GreetingConfig, GreetingRoutes }

import scala.concurrent.ExecutionContext.global

abstract class Test extends Spec1[IO] with AssertCIO with Matchers {
  override def config =
    super.config.copy(
      configBaseName = "ru.otus.sc",
      pluginConfig = Test.pluginConfig,
      memoizationRoots = Set(
        DIKey[Server],
        DIKey[GreetingClient]
      )
    )
}

object Test {
  val httpModule = new ConfigModuleDef {
    make[HttpRoutes[IO]].from(GreetingRoutes.apply _)
    make[Server].fromResource { (ce: ConcurrentEffect[IO], tm: Timer[IO], routes: HttpRoutes[IO]) =>
      BlazeServerBuilder[IO](global)(ce, tm)
        .bindLocal(8080)
        .withHttpApp(Router[IO]("/" -> routes).orNotFound)
        .resource
    }
    make[Client[IO]].fromResource { (cs: ContextShift[IO]) =>
      implicit val contextShift = cs
      BlazeClientBuilder[IO](global).resource
    }
    make[GreetingClient].from(GreetingClient.apply _)
  }

  val appModule = new ConfigModuleDef {
    makeConfig[Config]("test")
    make[GreetingConfig].from((_: Config).greeting)
  }

  val daoModule = new ConfigModuleDef {
    make[GreetingDao].from(GreetingDao.inmemory _)
    make[UserDao].from(UserDao.inmemory)
  }

  val serviceModule = new ConfigModuleDef {
    make[BotService[IO]].from(BotService.apply[IO] _)
    make[GreetingService].from(GreetingService.apply _)
    make[UserService].from(UserService.apply _)
  }

  object plugin extends PluginDef {
    include(appModule)
    include(daoModule)
    include(httpModule)
    include(serviceModule)
  }

  val pluginConfig = PluginConfig.const(plugin)
}
