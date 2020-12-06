package ru.otus.sc.greet

import cats.effect.{ ContextShift, IO }
import io.circe.syntax.EncoderOps
import org.http4s.client.Client
import org.http4s.server.Server
import org.http4s.{ EntityDecoder, Header, Request }
import ru.otus.sc.greet.model.{ Greeting, GreetingMethod, Id, User }
import org.http4s.circe.CirceEntityCodec.circeEntityDecoder
import org.http4s.circe.jsonEncoder
import org.http4s.client.dsl.io.http4sWithBodySyntax
import org.http4s.dsl.io.{ GET, POST }
import org.http4s.headers.`User-Agent`
import org.http4s.implicits.http4sLiteralsSyntax

/**
 * Клиент для обращения к API сервисов. Используется в тестах
 */
case class GreetingClient(client: Client[IO], cs: ContextShift[IO], server: Server) {
  private def expect[A](baseRequest: IO[Request[IO]])(implicit ed: EntityDecoder[IO, A]): IO[A] = {
    for {
      request  <- baseRequest.map { request =>
                    request.withUri(request.uri.copy(authority = server.baseUri.authority))
                  }
      response <- client.expect(request)
    } yield response
  }

  def createUser(user: User): IO[User] = {
    expect[User] {
      POST(user.asJson, uri"/users")
    }
  }

  def greetUser(id: Id[User]): IO[Greeting[User]] = {
    expect[Greeting[User]] {
      GET(uri"/users" / id.value.toString / "greet")
    }
  }

  def greetSubordinates(id: Id[User]): IO[List[Greeting[User]]] = {
    expect[List[Greeting[User]]] {
      GET(uri"/users" / id.value.toString / "subordinates" / "greet")
    }
  }

  def greet[A: GreetingMethod](userAgent: String)(implicit ed: EntityDecoder[IO, Greeting[A]]): IO[Greeting[A]] = {
    expect[Greeting[A]] {
      GET(uri"/greet").map(_.withHeaders(Header(`User-Agent`.name.toString, userAgent)))
    }
  }

  def findGreetings[A: GreetingMethod](id: String)(implicit
    ed: EntityDecoder[IO, List[Greeting[A]]]
  ): IO[List[Greeting[A]]] = {
    expect[List[Greeting[A]]] {
      GET(uri"/greetings"
        .+?("greet_method", GreetingMethod[A].entryName)
        .+?("id", id))
    }
  }
}
