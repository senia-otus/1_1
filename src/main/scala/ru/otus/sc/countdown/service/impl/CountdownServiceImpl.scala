package ru.otus.sc.countdown.service.impl

import ru.otus.sc.countdown.dao.CountdownDao
import ru.otus.sc.countdown.model
import ru.otus.sc.countdown.model.{CountdownClearRequest, CountdownDoneResponse, CountdownRequest, CountdownResponse}
import ru.otus.sc.countdown.service.CountdownService

class CountdownServiceImpl(dao: CountdownDao) extends CountdownService {
  def countdown(request: CountdownRequest): CountdownResponse = {
    // Can not use pattern matching here, so have to use ugly .isInstanceOf .asInstanceOf :(
    if (request.isInstanceOf[CountdownClearRequest])
      makeAnswer(dao.clearCountdown(request.asInstanceOf[CountdownClearRequest].initValue))
    else makeAnswer(dao.getCountdown)
  }

  private def makeAnswer(countdown: String): CountdownResponse = {
    if (countdown.toLong <= 0) CountdownDoneResponse(countdown)
    else model.CountdownCountResponse(countdown)
  }
}
