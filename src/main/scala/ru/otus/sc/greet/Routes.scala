package ru.otus.sc.greet

import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec.{ circeEntityDecoder, circeEntityEncoder }
import org.http4s.dsl.Http4sDsl
import ru.otus.sc.greet.model.{ Id, InvalidUserError, User, UserNotFoundError }
import ru.otus.sc.greet.service.{ GreetingService, UserService }

object Routes {
  def apply(userService: UserService, greetingService: GreetingService): HttpRoutes[IO] = {
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
    }
  }
}
