package ru.otus.sc.reverse.service.impl

import ru.otus.sc.reverse.dao.ReverseDao
import ru.otus.sc.reverse.model.{ReverseRequest, ReverseResponse}
import ru.otus.sc.reverse.service.ReverseService

class ReverseServiceImpl(dao: ReverseDao) extends ReverseService {

  def reverse(request: ReverseRequest): ReverseResponse = {
    ReverseResponse(dao.reverse(request.requestedWord))
  }
}
