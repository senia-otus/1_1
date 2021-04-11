package ru.otus.sc.greet.service.impl

import ru.otus.sc.greet.dao.GreetingDao
import ru.otus.sc.greet.dao.impl.GreetingDaoImpl
import ru.otus.sc.greet.model.{EchoRequest, GetValueRequest, GreetRequest, ServiceResponse}
import ru.otus.sc.greet.service.GreetingService

import java.util.concurrent.atomic.AtomicLong

class GreetingServiceImpl(dao: GreetingDao) extends GreetingService {

  private val cnt = new AtomicLong(0L)


  def greet(request: GreetRequest): ServiceResponse =
    withCounter {
      if (request.isHuman)
        ServiceResponse.good(s"${dao.greetingPrefix} ${request.name} ${dao.greetingPostfix}")
      else ServiceResponse.bad("AAAAAAAAAA!!!!!!")
    }

  def echo(request: EchoRequest): ServiceResponse =
    withCounter {
      ServiceResponse.good(s"${request.values.mkString(",")}")
    }

  def exit: ServiceResponse =
    withCounter {
      ServiceResponse.good(s"goodbye")
    }

  def getValue(request: GetValueRequest): ServiceResponse =
    withCounter {
      dao.data.get(request.key) match {
        case Some(value) =>
          ServiceResponse.good(value)
        case None =>
          ServiceResponse.bad( s"Data for key[${request.key}] not found")
      }
    }

  def counter: ServiceResponse = ServiceResponse.good(s"${cnt.get()}")

  private def withCounter[T](body: => T): T = {
    val _ = cnt.incrementAndGet()
    body
  }

}

object app1 extends App {

  val srv=new GreetingServiceImpl(new GreetingDaoImpl)

  println(srv.counter)

  println(srv.echo(EchoRequest(List("a","b"))))

  println(srv.getValue(GetValueRequest("1")))
  println(srv.getValue(GetValueRequest("1111111")))

  println(srv.counter)
  println(srv.exit)

}