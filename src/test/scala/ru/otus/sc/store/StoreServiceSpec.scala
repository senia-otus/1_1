package ru.otus.sc.store

import org.scalatest.wordspec.AnyWordSpec
import ru.otus.sc.App
import ru.otus.sc.store.model.StoreGetRequest

class StoreServiceSpec extends AnyWordSpec {
  val app: App = App()

  "A Store" should {
    "in default state" when {
      "get Boolean 'false' in 'c' key" in {
        assert(app.storeGet(StoreGetRequest("c")).value.toString == "false")
      }
      "return non empty list of keys" in {
        assert(app.storeGetKeys().keys.nonEmpty)
      }
    }
  }
}
