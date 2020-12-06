package ru.otus.sc.greet.service

import org.http4s.Request
import org.http4s.headers.`User-Agent`
import ru.otus.sc.greet.Config
import ru.otus.sc.greet.model.Bot

import scala.util.matching.Regex

trait BotService[F[_]] {
  def asBot(request: Request[F]): Option[Bot]
}

object BotService {
  def apply[F[_]](config: Config): BotService[F] =
    new BotService[F] {
      val patterns: Seq[Regex] = config.bots.map(_.r)

      override def asBot(request: Request[F]): Option[Bot] = {
        request.headers.get(`User-Agent`).flatMap { userAgent =>
          patterns
            .view
            .flatMap(_.findFirstIn(userAgent.value))
            .headOption
            .map(Bot.apply)
        }
      }
    }
}
