package ru.otus.sc.store

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import ru.otus.sc.App
import ru.otus.sc.store.model.StoreGetRequest

class StoreServiceSpec extends AnyWordSpec {
  val app: App = App()

  "A Store" should {
    "in default state" when {
      "get Boolean 'false' in 'c' key" in {
        app.storeGet(StoreGetRequest("c")).value.toString shouldEqual "false"
      }
      "return non empty list of keys" in {
        app.storeGetKeys().keys should not be empty
      }
    }
  }
}
