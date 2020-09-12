package ru.otus.sc.countdown.model

sealed trait CountdownRequest

case class CountdownClearRequest(initValue: Long = 1) extends CountdownRequest
case class CountdownTickRequest()                     extends CountdownRequest
