package ru.otus.sc.post

import akka.actor.typed.ActorSystem
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import ru.otus.sc.Application

import scala.concurrent.duration.DurationInt

class PostRoute(implicit val sys: ActorSystem[Nothing]) {

  import akka.http.scaladsl.server.Directives._
  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  private val app = Application()

  // asking someone requires a timeout and a scheduler, if the timeout hits without response
  // the ask is failed with a TimeoutException
  implicit val timeout: Timeout = 3.seconds
  val route: Route = pathPrefix("posts" / LongNumber) { id =>
    innerRoute(id)
  }

  private def innerRoute(userId: Long): Route = get {
    logRequest("GET-POSTS")
    pathEndOrSingleSlash {
      complete {
        app.findAllPosts(userId)
      }
    }
  } ~ pathPrefix("view" / LongNumber) { id =>
    get {
      logRequest("GET-POSTS-VIEW")
      pathEndOrSingleSlash {
        complete {
          app.findPost(id)
        }
      }
    }
  }

}
