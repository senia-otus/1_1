package ru.otus.sc.sum.service

import ru.otus.sc.sum.model.{SumRequest, SumResponse}

trait SumService {
  def sum(request: SumRequest): SumResponse
}
