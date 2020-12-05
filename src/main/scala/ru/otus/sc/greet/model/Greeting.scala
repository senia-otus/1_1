package ru.otus.sc.greet.model

import java.time.LocalDateTime

import enumeratum.{ Enum, EnumEntry }

case class Greeting[A](
  id: Option[Id[Greeting[A]]],
  greetedId: Option[Id[A]],
  greetingMethod: GreetingMethod[A],
  text: String,
  time: LocalDateTime
)

sealed trait GreetingMethod[A] extends EnumEntry {
  def id(someone: A): Option[Id[A]]
  def greet(someone: A): String
}

object GreetingMethod extends Enum[GreetingMethod[_]] {
  implicit object UserGreetingMethod extends GreetingMethod[User] {
    override def id(user: User): Option[Id[User]] = user.id
    override def greet(user: User): String        = user.name
  }

  implicit object GuestGreetingMethod extends GreetingMethod[Guest] {
    override def id(guest: Guest): Option[Id[Guest]] = None
    override def greet(guest: Guest): String         = "guest"
  }

  implicit object BotGreetingMethod extends GreetingMethod[Bot] {
    override def id(guest: Bot): Option[Id[Bot]] = None
    override def greet(bot: Bot): String         = bot.userAgent
  }

  override def values: IndexedSeq[GreetingMethod[_]] = findValues
}
