package ru.otus.sc.greet

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
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
        defaultGreet.isHuman shouldEqual true
      }
    }
    "from human" should {
      "have flag isHuman true" in {
        humanGreet.isHuman shouldEqual true
      }
      "have response with containing name" in {
        val response = app.greet(humanGreet)
        response.greeting should include(humanGreet.name)
      }
    }
    "from not human" should {
      "have flag isHuman false" in {
        notHumanGreet.isHuman should not equal true
      }
      "have response with no containing name" in {
        val response = app.greet(notHumanGreet)
        response.greeting should not include notHumanGreet.name
      }
    }
  }
}
