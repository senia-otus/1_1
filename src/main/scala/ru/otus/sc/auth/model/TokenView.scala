package ru.otus.sc.auth.model

import java.time.OffsetDateTime

case class TokenView(value: String, expiredAt: OffsetDateTime)
