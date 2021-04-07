package ru.otus.sc.`lazy`.service

import ru.otus.sc.`lazy`.model.{LazyRequest, LazyResponse}

/**
  * Сервис получения лениво вычисляемого значения
  */
trait LazyService {
  def get(request: LazyRequest): LazyResponse
}
