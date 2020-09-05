package ru.otus.sc.common

import java.time.OffsetDateTime

trait CommonResponseMixin {
  val date: OffsetDateTime = OffsetDateTime.now()
  val status: Int          = 200
}
