package ru.otus.sc

import ru.otus.sc.greet.dao.impl.GreetingDaoImpl
import ru.otus.sc.greet.model._
import ru.otus.sc.greet.service.GreetingService
import ru.otus.sc.greet.service.impl.GreetingServiceImpl
import ru.otus.sc.greet.ui.UI

trait App {
  def reply(request: Command): ServiceResponse
  def run(): Unit
}

object App {
  private class AppImpl(service: GreetingService, ui: UI) extends App {
    def reply(command: Command): ServiceResponse =
      command match {
        case Help                 => ServiceResponse.good(ui.help)
        case gr: GreetRequest     => service.greet(gr)
        case er: EchoRequest      => service.echo(er)
        case gvr: GetValueRequest => service.getValue(gvr)
        case CounterRequest       => service.counter
        case ExitRequest          => service.exit
      }

    override def run(): Unit = ui.run(reply)

  }

  def apply(): App = {
    val greetingDao     = new GreetingDaoImpl
    val greetingService = new GreetingServiceImpl(greetingDao)
    val ui              = UI.console
    new AppImpl(greetingService, ui)
  }

}
