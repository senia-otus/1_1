package ru.otus.sc.`lazy`.model

/**
  * Type-класс для хранения синглетона по типу
  * @tparam A тип
  */
trait LazyValue[A] {
  def get: A
}

object LazyValue {
  def apply[A](implicit l: LazyValue[A]): LazyValue[A] = l

  implicit class LazyValueSyntax[A](a: A) {
    def default()(implicit l: LazyValue[A]): A = l.get
  }

  implicit val intLazyValue = new LazyValue[Int] {
    lazy val get = 100_000_000
  }

  implicit val stringLazyValue = new LazyValue[String] {
    lazy val get = "A Big Hello!"
  }

}
