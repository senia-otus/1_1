package ru.otus.sc.reverse.service

import ru.otus.sc.reverse.model.{ReverseRequest, ReverseResponse}

trait ReverseService {
  def reverse(request: ReverseRequest): ReverseResponse
}
