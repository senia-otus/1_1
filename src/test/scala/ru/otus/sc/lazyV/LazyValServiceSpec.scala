package ru.otus.sc.lazyV

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import ru.otus.sc.App

class LazyValServiceSpec extends AnyWordSpec {
  "A LzayVal" should {
    "before call" when {
      val app: App = App()
      "have flag 'isCalled' == 'false'" in {
        app.lazyValueIsCaled().value shouldEqual false
      }
    }

    "after call" when {
      val app: App = App()
      val response = app.lazyValueGet()
      "have value in response" in {
        response.existingValue.toString should not be empty
      }

      "have flag 'isCalled' == 'true'" in {
        app.lazyValueIsCaled().value shouldEqual true
      }
    }

    "after next calls" when {
      val app: App  = App()
      val response1 = app.lazyValueGet()
      val response2 = app.lazyValueGet()

      "have same response" in {
        response1 shouldEqual response2
      }
      "have flag 'isCalled' == 'true'" in {
        app.lazyValueIsCaled().value shouldEqual true
      }
    }
  }
}
