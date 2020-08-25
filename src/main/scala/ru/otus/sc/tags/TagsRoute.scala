package ru.otus.sc.tags

import akka.actor.typed.ActorSystem
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import ru.otus.sc.Application

import scala.concurrent.duration.DurationInt

class TagsRoute(implicit val sys: ActorSystem[Nothing]) {

  import akka.http.scaladsl.server.Directives._
  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._

  private val app = Application()

  // asking someone requires a timeout and a scheduler, if the timeout hits without response
  // the ask is failed with a TimeoutException
  implicit val timeout: Timeout = 3.seconds
  val route: Route = pathPrefix("tags") {
    innerRoute()
  }

  private def innerRoute(): Route = get {
    complete {
      ""
    }
  }

}