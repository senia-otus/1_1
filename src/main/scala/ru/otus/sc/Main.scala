package ru.otus.sc

import cats.effect.{ ExitCode, IO, IOApp }
import org.http4s.implicits.http4sKleisliResponseSyntaxOptionT
import org.http4s.server.Router
import org.http4s.server.blaze.BlazeServerBuilder
import ru.otus.sc.greet.Routes
import ru.otus.sc.greet.dao.{ GreetingDao, UserDao }
import ru.otus.sc.greet.service.{ GreetingService, UserService }

import scala.concurrent.ExecutionContext.global

object Main extends IOApp {
  val userDao         = UserDao.inmemory
  val greetingDao     = GreetingDao.inmemory
  val userService     = UserService(userDao)
  val greetingService = GreetingService(greetingDao, userService)

  val httpRoutes = Router[IO]("/" -> Routes(userService, greetingService)).orNotFound

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
