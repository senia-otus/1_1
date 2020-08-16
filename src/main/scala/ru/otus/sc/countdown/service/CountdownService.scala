package ru.otus.sc.countdown.service

import ru.otus.sc.countdown.model.{CountdownRequest, CountdownResponse}

trait CountdownService {
  def countdown(request: CountdownRequest): CountdownResponse
}
