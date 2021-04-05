package ru.otus.sc.custom.service

import org.scalatest.freespec.AnyFreeSpec
import ru.otus.sc.App
import ru.otus.sc.custom.model.{ShowCountsRequest, EchoRequest}

class CustomServiceTest extends AnyFreeSpec {
  val app = App()

  "A Custom" - {
    "when send Show all methods contains more when 1 lines" - {
      val resp = app.showCountMethods()
      assert(resp.methods.count(_ == '\n') > 1)
    }

    "when send Show unexisting method, response is fail" - {
      val resp = app.showCounts(ShowCountsRequest("NO_METHOD"))
      assert(resp.isFail)
    }

    "when send Show method with no calls, response not fail and counts count equals 0" - {
      val resp = app.showCounts(ShowCountsRequest("isLazyInit"))
      assert(!resp.isFail)
      assert(resp.count == 0)
    }

    "when send Show method with calls, response not fail and contains count more than 1" - {
      val resp = app.showCounts(ShowCountsRequest("showCounts"))
      assert(!resp.isFail)
      assert(resp.count > 1)
    }

    "when send Echo method, response contains sended words" - {
      val words = "Sha-la-m-ba-la"
      val resp  = app.echo(EchoRequest(words))
      assert(resp.echoed == words)
    }

    "when send Test lazy value calls with no call lazy val, response contains that no calls is" - {
      val resp = app.isLazyInit
      assert(!resp.isInit)
    }

    "when send Get lazy value, response contains lazy val and next Test init lazy val is OK" - {
      val respLazyVal  = app.tryLazy()
      val respTestInit = app.isLazyInit

      assert(respLazyVal.lazyValue.nonEmpty)
      assert(respTestInit.isInit)
    }

  }
}
