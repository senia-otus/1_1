package ru.otus.sc.custom.model

trait LazyValue {}

case class LazyValRequest()                   extends LazyValue
case class LazyValResponse(lazyValue: String) extends LazyValue

case class TestLazyValRequest()                 extends LazyValue
case class TestLazyValResponse(isInit: Boolean) extends LazyValue
