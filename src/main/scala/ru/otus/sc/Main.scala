package ru.otus.sc

import cats.effect.{ ExitCode, IO, IOApp }
import org.http4s.implicits.http4sKleisliResponseSyntaxOptionT
import org.http4s.server.Router
import org.http4s.server.blaze.BlazeServerBuilder
import pureconfig.{ ConfigSource, _ }
import pureconfig.generic.auto._
import ru.otus.sc.greet.dao.{ GreetingDao, UserDao }
import ru.otus.sc.greet.service.{ BotService, GreetingService, UserService }
import ru.otus.sc.greet.{ Config, GreetingRoutes }

import scala.concurrent.ExecutionContext.global

object Main extends IOApp {
  val config          = ConfigSource.default.load[Config].getOrElse(throw new RuntimeException("Failed to load config"))
  val userDao         = UserDao.inmemory
  val greetingDao     = GreetingDao.inmemory(config.greeting)
  val botService      = BotService[IO](config)
  val userService     = UserService(userDao)
  val greetingService = GreetingService(greetingDao, userService)

  val httpRoutes = Router[IO]("/" -> GreetingRoutes(botService, userService, greetingService)).orNotFound

  override def run(args: List[String]): IO[ExitCode] = {
    BlazeServerBuilder[IO](global)
      .bindHttp(8080, "0.0.0.0")
      .withHttpApp(httpRoutes)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
  }
}
