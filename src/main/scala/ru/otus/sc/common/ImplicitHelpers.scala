package ru.otus.sc.common

import scala.concurrent.ExecutionContext

trait ImplicitHelpers {

  implicit val context         = ExecutionContext.global
  implicit def future[T](v: T) = new FutureUtil(v)

}
