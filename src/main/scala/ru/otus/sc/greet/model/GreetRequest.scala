package ru.otus.sc.greet.model

case class GreetRequest[T](name: T, isHuman: Boolean = true)(implicit conv: T => String) {
  def extractName(): String = conv(name)
}
