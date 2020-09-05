package ru.otus.sc.auth

import akka.actor.typed.ActorSystem
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import ru.otus.sc.Application
import ru.otus.sc.auth.model.{AuthRequest, RegisterRequest}
import ru.otus.sc.common.Logging

import scala.concurrent.duration.DurationInt

class AuthRoute(implicit val sys: ActorSystem[Nothing]) extends Logging {

  import akka.http.scaladsl.server.Directives._
  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  private val app = Application()

  // asking someone requires a timeout and a scheduler, if the timeout hits without response
  // the ask is failed with a TimeoutException
  implicit val timeout: Timeout = 3.seconds
  val route: Route = pathPrefix("sign") {
    innerRoute()
  }

  private def innerRoute(): Route =
    pathPrefix("in") {
      post {
        pathEndOrSingleSlash {
          entity(as[AuthRequest]) { request =>
            complete {
              app.auth(request)
            }
          }
        }
      }
    } ~ pathPrefix("up") {
      post {
        pathEndOrSingleSlash {
          entity(as[RegisterRequest]) { request =>
            complete {
              app.register(request)
            }
          }
        }
      }
    }

}
