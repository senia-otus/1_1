package ru.otus.sc.greet

import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec.{ circeEntityDecoder, circeEntityEncoder }
import org.http4s.dsl.Http4sDsl
import org.http4s.headers.`User-Agent`
import ru.otus.sc.greet.model.{ Bot, Id, InvalidUserError, User, UserNotFoundError }
import ru.otus.sc.greet.service.{ BotService, GreetingService, UserService }

object Routes {
  def apply(botService: BotService[IO], userService: UserService, greetingService: GreetingService): HttpRoutes[IO] = {
    val dsl = new Http4sDsl[IO] {}
    import dsl._

    HttpRoutes.of[IO] {
      case req @ POST -> Root / "users"                                     =>
        req.decode[User] { user =>
          userService.create(user) match {
            case Left(error: InvalidUserError)  => BadRequest(error)
            case Left(error: UserNotFoundError) => NotFound(error)
            case Right(user)                    => Ok(user)
          }
        }

      case _ @GET -> Root / "users" / IntVar(id) / "greet"                  =>
        greetingService.greetUser(Id(id)) match {
          case Left(error: UserNotFoundError) => BadRequest(error)
          case Right(greeting)                => Ok(greeting)
        }

      case _ @GET -> Root / "users" / IntVar(id) / "subordinates" / "greet" =>
        greetingService.greetSubordinates(Id(id)) match {
          case Left(error: UserNotFoundError)        =>
            BadRequest(error)
          case Right(greetings) if greetings.isEmpty =>
            NotFound(List.empty[User])
          case Right(greetings)                      =>
            Ok(greetings)
        }

      case req @ GET -> Root / "greet"                                      =>
        botService.asBot(req) match {
          case Some(bot) => Ok(greetingService.greetBot(bot))
          case None      => Ok(greetingService.greetGuest)
        }
    }
  }
}
