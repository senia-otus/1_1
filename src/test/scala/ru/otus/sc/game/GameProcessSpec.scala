package ru.otus.sc.game

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import ru.otus.sc.App
import ru.otus.sc.game.model.User
import ru.otus.sc.game.service.{GameRegisterRequest, GameService}

class GameProcessSpec extends AnyWordSpec {
  val game: GameService = App().game
  val user: User        = User("Saber", "saber", "qwe123")

  "Register user" should {
    "when send unique data" when {
      "get success answer" in {
        val req = game.startNewGame(GameRegisterRequest(user.nick, user.username, user.password))
        req.success shouldEqual true
      }
    }
  }
}
