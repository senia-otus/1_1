package ru.otus.sc.auth.model

import java.sql.Timestamp

case class TokenView(value: String, expiredAt: Timestamp)
