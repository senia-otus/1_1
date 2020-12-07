package ru.otus.sc.greet.model

import java.time.LocalDateTime

import derevo.circe.codec
import derevo.derive
import enumeratum.{ CirceEnum, Enum, EnumEntry }
import ru.otus.sc.greet.model.GreetingMethod.MapConstraint
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
@derive(codec)
case class Greeting(
  id: Option[Id[Greeting]],
  greetedId: Option[Long],
  greetingMethod: GreetingMethod,
  text: String,
  time: LocalDateTime
)

/**
 * Метод приветствия. Попытался сделать его тайпклассом для [[GreetingDao]].
 */
sealed trait GreetingMethod extends EnumEntry {
  type Greeted
  type Self

  /**
   * Возвращает идентификатор приветствуемой сущности, если её можно идентифицировать (например, пользователь)
   */
  def id(someone: Greeted): Option[Long]

  /**
   * Генерирует текст приветствия для сущности
   */
  def greet(someone: Greeted): String

  /**
   * Возвращает констрейнт для гетерогенной map с методами приветствий
   */
  def constraint: MapConstraint[Self, MapConstraint.GreetingMethodMap]
}

sealed trait GreetingMethodTyped[A] extends GreetingMethod {
  override type Greeted = A
  override type Self    = GreetingMethodTyped[A]
}

object GreetingMethod extends Enum[GreetingMethod] with CirceEnum[GreetingMethod] {

  /**
   * Констрейнт для гетерогенной мапы с методами приветствий
   */
  case class MapConstraint[-K, V]()
  object MapConstraint {
    type GreetingMethodMap = TrieMap[Id[Greeting], Greeting]
    implicit lazy val userMap  = new MapConstraint[GreetingMethodTyped[User], GreetingMethodMap]
    implicit lazy val guestMap = new MapConstraint[GreetingMethodTyped[Guest], GreetingMethodMap]
    implicit lazy val botMap   = new MapConstraint[GreetingMethodTyped[Bot], GreetingMethodMap]
  }

  implicit case object UserGreetingMethod extends GreetingMethod with GreetingMethodTyped[User] {
    override def id(user: User): Option[Long] = user.id.map(_.value)
    override def greet(user: User): String    = user.name
    override def constraint                   = MapConstraint.userMap
  }

  implicit case object GuestGreetingMethod extends GreetingMethod with GreetingMethodTyped[Guest] {
    override def id(guest: Guest): Option[Long] = None
    override def greet(guest: Guest): String    = "guest"
    override def constraint                     = MapConstraint.guestMap
  }

  implicit case object BotGreetingMethod extends GreetingMethod with GreetingMethodTyped[Bot] {
    override def id(guest: Bot): Option[Long] = None
    override def greet(bot: Bot): String      = bot.userAgent
    override def constraint                   = MapConstraint.botMap
  }

  override def values: IndexedSeq[GreetingMethod] = findValues

  /**
   * Метод создания гетерогенной map с методами приветствий, каждый элемент которой содержит мапу приветствий этого метода.
   */
  def toMap: HMap[MapConstraint] = {
    GreetingMethod.values.foldLeft(new HMap[MapConstraint]) { (map, gm) =>
      gm match {
        case method: UserGreetingMethod.type  => map + (method -> TrieMap.empty[Id[Greeting], Greeting])
        case method: GuestGreetingMethod.type => map + (method -> TrieMap.empty[Id[Greeting], Greeting])
        case method: BotGreetingMethod.type   => map + (method -> TrieMap.empty[Id[Greeting], Greeting])
      }
    }
  }
}
