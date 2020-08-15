package ru.otus.sc.sum.service.impl

import ru.otus.sc.sum.dao.SumDao
import ru.otus.sc.sum.model.{SumRequest, SumResponse}
import ru.otus.sc.sum.service.SumService

class SumServiceImpl(dao: SumDao) extends SumService {

  def sum(request: SumRequest): SumResponse = {
    SumResponse(dao.sum(request.a, request.b, request.external))
  }
}
