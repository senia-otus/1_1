package ru.otus.sc

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.DebuggingDirectives
import org.flywaydb.core.Flyway
import ru.otus.sc.Migrator.migrate
import ru.otus.sc.auth.AuthRoute
import ru.otus.sc.comment.CommentRoute
import ru.otus.sc.common.Logging
import ru.otus.sc.config.{ApplicationConfig, DatabaseConfig}
import ru.otus.sc.greet.GreetRoute
import ru.otus.sc.post.PostRoute
import ru.otus.sc.tags.TagsRoute
import ru.otus.sc.user.UserRoute

import scala.io.StdIn

object Main extends App with Logging {

  logger.info("Application warming...")

  logger.info("Load settings...")
  val (app, flyway, db) = ApplicationConfig.loadSettings()
  logger.debug(s"settings: $app, $flyway, $db")
  logger.info("Settings loaded successfully.")

  if (flyway.enabled) {
    logger.info("Flyway starts migrations...")
    migrate(db)
    logger.info("Flyway migrations completed.")
  }

  logger.info("Warming actor system...")
  implicit val system = ActorSystem(Behaviors.empty, "my-system")
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.executionContext

  val route =
    path("hello") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
      }
    }

  logger.info("Assembling http server...")
  val bindingFuture = Http()
    .newServerAt(app.host, app.port)
    .bind(
      concat(
        DebuggingDirectives.logRequestResult("Client REST", Logging.InfoLevel)(
          new GreetRoute().route
        ),
        DebuggingDirectives.logRequestResult("Client REST", Logging.InfoLevel)(
          new AuthRoute().route
        ),
        DebuggingDirectives.logRequestResult("Client REST", Logging.InfoLevel)(
          new PostRoute().route
        ),
        DebuggingDirectives.logRequestResult("Client REST", Logging.InfoLevel)(
          new UserRoute().route
        ),
        DebuggingDirectives.logRequestResult("Client REST", Logging.InfoLevel)(
          new TagsRoute().route
        ),
        DebuggingDirectives.logRequestResult("Client REST", Logging.InfoLevel)(
          new CommentRoute().route
        )
      )
    )

  logger.info(s"Server online at http://${app.host}:${app.port}/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind())                 // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done

}

object Migrator extends Logging {

  def migrate(config: DatabaseConfig) = {
    val (url, user, password) = DatabaseConfig.unapply(config).get
    val flyway                = Flyway.configure.dataSource(url, user, password).load
    flyway.migrate()
  }

}
