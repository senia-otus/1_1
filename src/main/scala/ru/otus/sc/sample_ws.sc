import ru.otus.sc._
import ru.otus.sc.custom.model._
import ru.otus.sc.greet.model._
val app = App()
app.greet(GreetRequest("Alex", true))
app.greet(GreetRequest("PC", false))
app.showCounts(ShowCountsRequest("isLazyInit"))
app.isLazyInit
app.tryLazy()
app.isLazyInit
app.showCounts(ShowCountsRequest("isLazyInit"))
app.echo(EchoRequest("Hello!"))
app.showCounts(ShowCountsRequest("echo"))
app.showCounts(ShowCountsRequest("showCountMethods"))
app.showCounts(ShowCountsRequest("unknownMethod"))
