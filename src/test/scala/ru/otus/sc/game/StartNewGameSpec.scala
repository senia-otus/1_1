package ru.otus.sc.game

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import ru.otus.sc.App
import ru.otus.sc.game.service.GameRegisterRequest

class StartNewGameSpec extends AnyWordSpec {
  /*"At start game" should {
    "when get all users" when {
      "contains one [Guest] user" in {
        val app   = App()
        val users = app.game.listOfUsers().users
        users.length shouldEqual 1
        users.head.nick shouldEqual "Guest"
      }
    }

    "when register new user" when {
      "get bad response with busy nick and username" in {
        val app      = App()
        val response = app.game.startNewGame(GameRegisterRequest("Guest", "myGuest", "123"))
        response.success shouldEqual false
        response.error should include("уже занят")

        val response2 = app.game.startNewGame(GameRegisterRequest("user", "guest", "123"))
        response2.success shouldEqual false
        response2.error should include("уже занят")
      }
      "get success response with unique nick and username" in {
        val app      = App()
        val response = app.game.startNewGame(GameRegisterRequest("Tricky", "tricks", "qwer"))
        response.success shouldEqual true
        response.error shouldEqual ""
      }
    }
  }*/
}
