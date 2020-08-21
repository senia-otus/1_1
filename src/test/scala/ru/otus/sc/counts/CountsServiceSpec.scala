package ru.otus.sc.counts

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import ru.otus.sc.App
import ru.otus.sc.counts.model.{CountOfRequest, CountUpRequest}
import ru.otus.sc.greet.model.GreetRequest

class CountsServiceSpec extends AnyWordSpec {
  val callCounter: CountUpRequest = CountUpRequest("custom-325")

  "A Call Counter" when {
    "no calls" should {
      val app: App = App()
      "have empty key-value map" in {
        val result = app.countsAll()
        result.info shouldBe empty
      }
      "return zero with request" in {
        val result = app.countOf(CountOfRequest(callCounter.key))
        result.counts shouldEqual 0
      }
    }
    "increment custom counter" should {
      val app: App   = App()
      val incRequest = app.customCountUp(callCounter)
      val allMap     = app.countsAll()
      "return response with change" in {
        incRequest.from should not equal incRequest.to
        incRequest.to should not equal 0
      }
      "have none empty key-value map" in {
        allMap.info should not be empty
      }
      "have name of custom call counter" in {
        allMap.info.keys should contain(callCounter.key)
      }
    }
    "increment greet call counter" should {
      val app: App = App()
      app.greet(GreetRequest("Den"))

      "have registered call counter 'greet'" in {
        val allMap = app.countsAll()
        allMap.info.keys should contain("greet")
      }
      "have count of call counter 'greet' eq 1" in {
        val response = app.countOf(CountOfRequest("greet"))
        response.counts shouldEqual 1
      }
    }
  }
}
