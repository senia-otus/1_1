package ru.otus.sc.auth.model

import java.time.OffsetDateTime

case class Token(value: String, expiredAt: OffsetDateTime)
