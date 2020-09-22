package ru.otus.sc.game

import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec
import ru.otus.sc.App
import ru.otus.sc.game.model.{SignInRequest, User}
import ru.otus.sc.game.service.GameService

class SignInSpec extends AnyWordSpec {
  val game: GameService = App().game
  val user: User        = User(nick = "Guest", username = "guest")

  "Sign in Default user" should {
    "when first send sign in request" when {
      val resp = game.signIn(SignInRequest(user.username))

      "get success answer" in {
        resp.success shouldEqual true
      }
      "get identical number" in {
        resp.data.nonEmpty shouldEqual true
      }
    }
    "when send sign in request next time" when {
      val resp = game.signIn(SignInRequest(user.username))

      "get success result" in {
        resp.success shouldEqual true
      }
      "get identical number" in {
        resp.data.nonEmpty shouldEqual true
      }
    }
    "when send unregistered username" when {
      val resp = game.signIn(SignInRequest("failed"))

      "get failed answer" in {
        resp.success shouldEqual false
      }
      "get error in info" in {
        resp.data shouldEqual "Player with username doesn't exists"
      }
    }
  }
}
