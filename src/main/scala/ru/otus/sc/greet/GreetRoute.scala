package ru.otus.sc.greet

import akka.actor.typed.ActorSystem
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import ru.otus.sc.Application
import ru.otus.sc.greet.model.GreetRequest

import scala.concurrent.duration.DurationInt

final class GreetRoute(implicit val sys: ActorSystem[Nothing]) {

  import akka.http.scaladsl.server.Directives._
  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  private val app = Application()

  // asking someone requires a timeout and a scheduler, if the timeout hits without response
  // the ask is failed with a TimeoutException
  implicit val timeout: Timeout = 3.seconds
  val route: Route = path("greetings") {
      innerRoute()
    }

  private def innerRoute(): Route = post {
    pathEndOrSingleSlash {
      entity(as[GreetRequest]) { request =>
        complete {
          app.greet(request)
        }
      }
    }
  }

}
