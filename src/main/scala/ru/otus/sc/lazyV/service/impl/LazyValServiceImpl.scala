package ru.otus.sc.lazyV.service.impl

import ru.otus.sc.lazyV.dao.LazyValDao
import ru.otus.sc.lazyV.model.LazyValResponse
import ru.otus.sc.lazyV.service.LazyValService

class LazyValServiceImpl(dao: LazyValDao) extends LazyValService {
  override def getLazyValue: LazyValResponse =
    LazyValResponse(dao.getLazyVal)
}
