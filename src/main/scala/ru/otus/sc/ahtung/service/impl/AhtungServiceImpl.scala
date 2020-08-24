package ru.otus.sc.ahtung.service.impl

import ru.otus.sc.ahtung.dao.AhtungDao
import ru.otus.sc.ahtung.model.{AhtungRequest, AhtungResponse, ErrorResponse, Response}
import ru.otus.sc.ahtung.service.AhtungService

class AhtungServiceImpl(dao: AhtungDao) extends AhtungService {
  override def ahtung(request: AhtungRequest): Response = {
    dao.get(request.index) match {
      case Some(value) => AhtungResponse(value)
      case _ => ErrorResponse(s"Index '${request.index}' out of bounds!")
    }
  }
}
