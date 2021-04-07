import ru.otus.sc.App
import ru.otus.sc.`lazy`.model.LazyRequest
import ru.otus.sc.counter.model.{ApiRequest1, ApiRequest2}
import ru.otus.sc.echo.model.EchoRequest
import ru.otus.sc.greet.model.GreetRequest
import ru.otus.sc.storage.model.{
  StorageDeleteBulkRequest,
  StorageDeleteRequest,
  StorageFindBulkRequest,
  StorageFindRequest,
  StorageUpdateBulkRequest,
  StorageUpdateRequest
}

object Main {
  def main(args: Array[String]): Unit = {
    args.headOption.getOrElse("") match {
      case "Echo" =>
        val res = App().ping(EchoRequest("Hello!"))
        println(res)
      case "Storage" =>
        val Key1      = "Key1"
        val app       = App()
        val updateRes = app.update(StorageUpdateRequest(Key1, "Value1"))
        println(s"Ключ $Key1 обновлен: ${updateRes.updated}")
        val findRes = app.findBy(StorageFindRequest(Key1))
        println(s"Ключ $Key1 найден: ${findRes.exists}")
        var delRes = app.delete(StorageDeleteRequest(Key1))
        println(s"Ключ $Key1 удален: ${delRes.deleted}")
        val findRes2 = app.findBy(StorageFindRequest(Key1))
        println(s"Ключ $Key1 найден: ${findRes2.exists}")
        val Key666     = "Key666"
        val updateRes2 = app.update(StorageUpdateRequest(Key666, "Value666"))
        println(s"Ключ $Key666 обновлен: ${updateRes2.updated}")
      case "StorageBulk" =>
        val Key1   = "Key1"
        val Key2   = "Key2"
        val Key666 = "Key666"
        val keys   = Key1 :: Key2 :: Key666 :: Nil
        val app    = App()
        val updateRes = app.updateBulk(
          StorageUpdateBulkRequest(List((Key1, "Value1"), (Key2, "Value2"), (Key666, "Value666")))
        )
        println(s"Ключи обновлены: ${updateRes.keys}")
        val findRes = app.findBy(StorageFindBulkRequest(keys))
        println(s"Ключи найдены: ${findRes.values}")
        var delRes = app.delete(StorageDeleteBulkRequest(keys))
        println(s"Ключи удалены: ${delRes}")
        val findRes2 = app.findBy(StorageFindBulkRequest(keys))
        println(s"Ключи найдены: ${findRes2}")
      case "Lazy" =>
        val app = App()
        val res = app.getLazyValue(LazyRequest("Int"))
        println(s"Int: $res")
        val res2 = app.getLazyValue(LazyRequest("String"))
        println(s"String: $res2")
        val res3 = app.getLazyValue(LazyRequest("Boolean"))
        println(s"Boolean: $res3")
      case "Counter" =>
        val app = App()
        val res = app.method1(ApiRequest1("Hello1_1!"))
        println(s"Res: $res")
        val res2 = app.method2(ApiRequest2("Hello2_1!"))
        println(s"Res: $res2")
        val res3 = app.method2(ApiRequest2("Hello2_2!"))
        println(s"Res: $res3")
        val res4 = app.method2(ApiRequest2("Hello2_3!"))
        println(s"Res: $res4")
      case _ =>
        val res = App().greet(GreetRequest("Alex"))
        println(res)
    }

  }
}
