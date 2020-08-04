package ru.otus.sc.greet

import org.scalatest.wordspec.AnyWordSpec
import ru.otus.sc.App
import ru.otus.sc.greet.model.GreetRequest

class GreetingServiceSpec extends AnyWordSpec {
  val app: App                    = App()
  val defaultGreet: GreetRequest  = GreetRequest("Maria")
  val humanGreet: GreetRequest    = GreetRequest("John", isHuman = true)
  val notHumanGreet: GreetRequest = GreetRequest("John Konnor", isHuman = false)

  "A greet" when {
    "with default" should {
      "have default flag isHuman true" in {
        assert(defaultGreet.isHuman)
      }
    }
    "from human" should {
      "have flag isHuman true" in {
        assert(humanGreet.isHuman)
      }
      "have response with containing name" in {
        val response = app.greet(humanGreet)
        assert(response.greeting.contains(humanGreet.name))
      }
    }
    "from not human" should {
      "have flag isHuman false" in {
        assert(!notHumanGreet.isHuman)
      }
      "have response with no containing name" in {
        val response = app.greet(notHumanGreet)
        assert(!response.greeting.contains(notHumanGreet.name))
      }
    }
  }
}
