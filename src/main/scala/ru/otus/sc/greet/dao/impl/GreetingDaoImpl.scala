package ru.otus.sc.greet.dao.impl

import ru.otus.sc.greet.dao.GreetingDao

class GreetingDaoImpl extends GreetingDao {

  val greetingPrefix: String  = "Hi"
  val greetingPostfix: String = "!"

  lazy val data: Map[String, String] =
    Map(
      "1" -> "one",
      "2" -> "two",
      "3" -> "three",
      "4" -> "four",
      "5" -> "five"
    )
}
