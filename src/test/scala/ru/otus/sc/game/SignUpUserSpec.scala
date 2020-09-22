package ru.otus.sc.game

import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec
import ru.otus.sc.App
import ru.otus.sc.game.model.{SignUpRequest, User}
import ru.otus.sc.game.service.GameService

class SignUpUserSpec extends AnyWordSpec {
  val game: GameService = App().game
  val user: User        = User(nick = "Saber", username = "saber")

  "Register user" should {
    "when send unique data" when {
      "get success answer" in {
        val req = game.signUp(SignUpRequest(user.nick, user.username))
        req.success shouldEqual true
      }
    }
    "when duplicate register" when {
      "get fail result" in {
        val req = game.signUp(SignUpRequest(user.nick, user.username))
        req.success shouldEqual false
      }
    }
  }
}
