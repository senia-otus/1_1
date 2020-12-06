package ru.otus.sc.greet

import cats.effect.IO
import io.circe.Encoder
import org.http4s.{ HttpRoutes, Response }
import org.http4s.circe.CirceEntityCodec.{ circeEntityDecoder, circeEntityEncoder }
import org.http4s.dsl.Http4sDsl
import org.http4s.headers.`User-Agent`
import ru.otus.sc.greet.model.{ Bot, Greeting, GreetingMethod, Id, InvalidUserError, User, UserNotFoundError }
import ru.otus.sc.greet.model.GreetingMethod.{ BotGreetingMethod, GuestGreetingMethod, UserGreetingMethod }
import ru.otus.sc.greet.service.{ BotService, GreetingService, UserService }

object Routes {
  def apply(botService: BotService[IO], userService: UserService, greetingService: GreetingService): HttpRoutes[IO] = {
    val dsl = new Http4sDsl[IO] {}
    import dsl._

    object greetMethod extends QueryParamDecoderMatcher[String]("greet_method")
    object identity    extends QueryParamDecoderMatcher[String]("id")

    def renderOrNotFound[A: Encoder](list: List[A]): IO[Response[IO]] = {
      list match {
        case Nil => NotFound(List.empty[A])
        case nel => Ok(nel)
      }
    }

    HttpRoutes.of[IO] {
      case req @ POST -> Root / "users"                                        =>
        req.decode[User] { user =>
          userService.create(user) match {
            case Left(error: InvalidUserError)  => BadRequest(error)
            case Left(error: UserNotFoundError) => NotFound(error)
            case Right(user)                    => Ok(user)
          }
        }

      case _ @GET -> Root / "users" / IntVar(id) / "greet"                     =>
        greetingService.greetUser(Id(id)) match {
          case Left(error: UserNotFoundError) => BadRequest(error)
          case Right(greeting)                => Ok(greeting)
        }

      case _ @GET -> Root / "users" / IntVar(id) / "subordinates" / "greet"    =>
        greetingService.greetSubordinates(Id(id)) match {
          case Left(error: UserNotFoundError)        =>
            BadRequest(error)
          case Right(greetings) if greetings.isEmpty =>
            NotFound(List.empty[User])
          case Right(greetings)                      =>
            Ok(greetings)
        }

      case req @ GET -> Root / "greet"                                         =>
        botService.asBot(req) match {
          case Some(bot) => Ok(greetingService.greetBot(bot))
          case None      => Ok(greetingService.greetGuest)
        }

      case _ @GET -> Root / "greetings" :? greetMethod(method) +& identity(id) =>
        GreetingMethod.withName(method) match {
          case method: UserGreetingMethod.type  =>
            renderOrNotFound(greetingService.findGreetings(Some(Id(id.toInt)), None)(method))
          case method: GuestGreetingMethod.type =>
            renderOrNotFound(greetingService.findGreetings(None, Some(id))(method))
          case method: BotGreetingMethod.type   =>
            renderOrNotFound(greetingService.findGreetings(None, Some(id))(method))
        }
    }
  }
}
