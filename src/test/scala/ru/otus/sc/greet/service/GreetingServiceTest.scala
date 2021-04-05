package ru.otus.sc.greet.service

import org.scalatest.freespec.AnyFreeSpec
import ru.otus.sc.App
import ru.otus.sc.greet.model.GreetRequest

class GreetingServiceTest extends AnyFreeSpec {
  val app  = App()
  val name = "Jack"

  "A Greet" - {
    "when from Human" - {
      val resp = app.greet(GreetRequest(name, true))
      assert(resp.greeting.contains(name))
    }
    "when from Not Human" - {
      val resp = app.greet(GreetRequest(name, false))
      assert(!resp.greeting.contains(name))
    }
  }
}
