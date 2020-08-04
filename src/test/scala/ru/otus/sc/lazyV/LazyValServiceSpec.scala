package ru.otus.sc.lazyV

import org.scalatest.wordspec.AnyWordSpec
import ru.otus.sc.App

class LazyValServiceSpec extends AnyWordSpec {
  "A LzayVal" should {
    "before call" when {
      val app: App = App()
      "have flag 'isCalled' == 'false'" in {
        assert(!app.lazyValueIsCaled().value)
      }
    }

    "after call" when {
      val app: App = App()
      val response = app.lazyValueGet()
      "have value in response" in {
        assert(response.existingValue.toString != "")
      }

      "have flag 'isCalled' == 'true'" in {
        assert(app.lazyValueIsCaled().value)
      }
    }

    "after next calls" when {
      val app: App  = App()
      val response1 = app.lazyValueGet()
      val response2 = app.lazyValueGet()

      "have same response" in {
        assert(response1 == response2)
      }
      "have flag 'isCalled' == 'true'" in {
        assert(app.lazyValueIsCaled().value)
      }
    }
  }
}
