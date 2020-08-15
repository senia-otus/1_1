package ru.otus.sc.counter.model

case class CounterRequest(clear: Boolean = false, initialValue: Long = 0)
