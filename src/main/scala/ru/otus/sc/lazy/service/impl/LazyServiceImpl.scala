package ru.otus.sc.`lazy`.service.impl

import ru.otus.sc.`lazy`.dao.LazyDao
import ru.otus.sc.`lazy`.model.{LazyRequest, LazyResponse}
import ru.otus.sc.`lazy`.service.LazyService

class LazyServiceImpl(dao: LazyDao) extends LazyService {
  def get(request: LazyRequest): LazyResponse =
    request.name.toLowerCase match {
      case "int"    => LazyResponse(dao.singleton[Int])
      case "string" => LazyResponse(dao.singleton[String])
      case _        => LazyResponse(null, exists = false)
    }
}
