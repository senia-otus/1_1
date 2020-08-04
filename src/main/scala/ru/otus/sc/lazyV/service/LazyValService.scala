package ru.otus.sc.lazyV.service

import ru.otus.sc.lazyV.model.LazyValResponse

trait LazyValService {
  def getLazyValue: LazyValResponse
}
