package ru.otus.sc.echo

import org.scalatest.wordspec.AnyWordSpec
import ru.otus.sc.App
import ru.otus.sc.echo.model.EchoRequest

class EchoServiceSpec extends AnyWordSpec {
  "An Echo" should {
    "when it calls" when {
      "have same value" in {
        val app: App = App()
        val request  = EchoRequest("Sample line with words")
        val response = app.echo(request)
        assert(request.value == response.value)
      }
    }
  }
}
