package ru.otus.sc.ahtung.service

import ru.otus.sc.ahtung.model.{AhtungRequest, Response}

trait AhtungService {
  def ahtung(request: AhtungRequest): Response
}
