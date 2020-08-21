package ru.otus.sc.common

import scala.concurrent.Future

class FutureUtil[T](value: T) {
  def future() = Future.successful(value)
}

