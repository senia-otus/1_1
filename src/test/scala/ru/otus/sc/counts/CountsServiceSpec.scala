package ru.otus.sc.counts

import org.scalatest.wordspec.AnyWordSpec
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
        assert(result.info.isEmpty)
      }
      "return zero with request" in {
        val result = app.countOf(CountOfRequest(callCounter.key))
        assert(result.counts == 0)
      }
    }
    "increment custom counter" should {
      val app: App   = App()
      val incRequest = app.customCountUp(callCounter)
      val allMap     = app.countsAll()
      "return response with change" in {
        assert(incRequest.from < incRequest.to)
        assert(incRequest.to != 0)
      }
      "have none empty key-value map" in {
        assert(allMap.info.nonEmpty)
      }
      "have name of custom call counter" in {
        assert(allMap.info.contains(callCounter.key))
      }
    }
    "increment greet call counter" should {
      val app: App = App()
      app.greet(GreetRequest("Den"))

      "have registered call counter 'greet'" in {
        val allMap = app.countsAll()
        assert(allMap.info.contains("greet"))
      }
      "have count of call counter 'greet' eq 1" in {
        val response = app.countOf(CountOfRequest("greet"))
        assert(response.counts == 1)
      }
    }
  }
}
