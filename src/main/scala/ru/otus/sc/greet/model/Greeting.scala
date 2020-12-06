package ru.otus.sc.greet.model

import java.time.LocalDateTime

import derevo.derive
import derevo.circe.encoder
import enumeratum.{ Enum, EnumEntry }
import io.circe.Decoder.decodeString
import io.circe.Encoder.encodeString
import io.circe.{ Decoder, Encoder }
import ru.otus.sc.greet.model.GreetingMethod.{ MapConstraint }
import shapeless.HMap

import scala.collection.concurrent.TrieMap

/**
 * Приветствие
 * @param id             идентификатор приветствия
 * @param greetedId      идентификатор приветствуемого
 * @param greetingMethod метод приветствия
 * @param text           текст приветствия
 * @param time           время приветствия
 */
@derive(encoder)
case class Greeting[A](
  id: Option[Id[Greeting[A]]],
  greetedId: Option[Id[A]],
  greetingMethod: GreetingMethod[A],
  text: String,
  time: LocalDateTime
)

/**
 * Метод приветствия. Попытался сделать его тайпклассом для [[GreetingDao]].
 */
sealed trait GreetingMethod[A] extends EnumEntry {
  /**
   * Возвращает идентификатор приветствуемой сущности, если её можно идентифицировать (например, пользователь)
   */
  def id(someone: A): Option[Id[A]]

  /**
   * Генерирует текст приветствия для сущности
   */
  def greet(someone: A): String

  /**
   * Возвращает констрейнт для гетерогенной map с методами приветствий
   */
  def constraint: MapConstraint[GreetingMethod[A], MapConstraint.GreetingMethodMap[A]]
}

object GreetingMethod extends Enum[GreetingMethod[_]] {
  /**
   * Констрейнт для гетерогенной мапы с методами приветствий
   */
  case class MapConstraint[-K, V]()
  object MapConstraint {
    type GreetingMethodMap[A] = TrieMap[Id[Greeting[A]], Greeting[A]]
    implicit lazy val userMap  = new MapConstraint[GreetingMethod[User], GreetingMethodMap[User]]
    implicit lazy val guestMap = new MapConstraint[GreetingMethod[Guest], GreetingMethodMap[Guest]]
    implicit lazy val botMap   = new MapConstraint[GreetingMethod[Bot], GreetingMethodMap[Bot]]
  }

  implicit case object UserGreetingMethod extends GreetingMethod[User] {
    override def id(user: User): Option[Id[User]] = user.id
    override def greet(user: User): String        = user.name
    override def constraint                       = MapConstraint.userMap
  }

  implicit case object GuestGreetingMethod extends GreetingMethod[Guest] {
    override def id(guest: Guest): Option[Id[Guest]] = None
    override def greet(guest: Guest): String         = "guest"
    override def constraint                          = MapConstraint.guestMap
  }

  implicit case object BotGreetingMethod extends GreetingMethod[Bot] {
    override def id(guest: Bot): Option[Id[Bot]] = None
    override def greet(bot: Bot): String         = bot.userAgent
    override def constraint                      = MapConstraint.botMap
  }

  override def values: IndexedSeq[GreetingMethod[_]] = findValues

  implicit def encoder[A]: Encoder[GreetingMethod[A]] = encodeString.contramap(_.entryName)
  implicit val decoder: Decoder[GreetingMethod[_]]    = decodeString.map(GreetingMethod.withName)

  /**
   * Метод создания гетерогенной map с методами приветствий, каждый элемент которой содержит мапу приветствий этого метода.
   */
  def toMap: HMap[MapConstraint] = {
    GreetingMethod.values.foldLeft(new HMap[MapConstraint]) { (map, gm) =>
      gm match {
        case method @ UserGreetingMethod  => map + (method -> TrieMap.empty[Id[Greeting[User]], Greeting[User]])
        case method @ GuestGreetingMethod => map + (method -> TrieMap.empty[Id[Greeting[Guest]], Greeting[Guest]])
        case method @ BotGreetingMethod   => map + (method -> TrieMap.empty[Id[Greeting[Bot]], Greeting[Bot]])
      }
    }
  }
}
