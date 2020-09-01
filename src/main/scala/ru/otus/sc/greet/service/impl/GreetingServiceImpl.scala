package ru.otus.sc.greet.service.impl

import ru.otus.sc.greet.dao.GreetingDao
import ru.otus.sc.greet.model.{GreetRequest, GreetResponse}
import ru.otus.sc.greet.service.GreetingService

class GreetingServiceImpl[T](dao: GreetingDao) extends GreetingService[T] {
  def greet(request: GreetRequest[T]): GreetResponse =
    if (request.isHuman)
      GreetResponse(s"${dao.greetingPrefix} ${request.extractName()} ${dao.greetingPostfix}")
    else GreetResponse("AAAAAAAAAA!!!!!!")
}
