package ru.otus.sc.greet.dao

import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicInteger

import ru.otus.sc.greet.GreetingConfig
import ru.otus.sc.greet.model._

/**
 * *
 * Dao для хранения приветствий
 */
trait GreetingDao {

  /**
   * Создаёт приветствие
   * @param someone приветствуемая сущность (пользователь, бот, гость)
   * @param gm      метод приветствия
   * @return        приветствие для данной сущности
   */
  def greet[A](someone: A)(implicit gm: GreetingMethodTyped[A]): Greeting

  /**
   * Поиск приветствий
   * @param id   идентификатор сущности (возможен только пользователь, т.к. для ботов и гостей пользователи не создаются)
   * @param text текст приветствия
   * @param gm   метод приветствия
   * @return     список приветствий
   */
  def findGreetings[A](id: Option[Id[A]], text: Option[String])(implicit gm: GreetingMethodTyped[A]): List[Greeting]
}

object GreetingDao {
  def inmemory(config: GreetingConfig): GreetingDao =
    new GreetingDao {
      //счётчик для генерации идентификаторов приветствий
      private val counter   = new AtomicInteger
      //гетерогенная map для хранения приветствий разных типов сущностей: пользователи, боты, гости
      private val greetings = GreetingMethod.toMap

      //получение map для конкретного метода приветствия из гетерогенной map
      //полнота заполнения гетерогенной map гарантируется паттерн матчингом на GreetingMethod
      private def getMethodMap[A](gm: GreetingMethodTyped[A]) = {
        greetings
          .get(gm)(gm.constraint)
          .getOrElse(throw new RuntimeException(s"Unregistered greeting method: $gm. Should never happen"))
      }

      override def greet[A](someone: A)(implicit gm: GreetingMethodTyped[A]): Greeting = {
        val id       = Id(counter.incrementAndGet())
        val greeting = Greeting(
          Some(id),
          gm.id(someone),
          gm,
          s"${config.prefix} ${gm.greet(someone)} ${config.postfix}",
          LocalDateTime.now
        )
        getMethodMap(gm).update(id, greeting)
        greeting
      }

      override def findGreetings[A](id: Option[Id[A]], text: Option[String])(implicit
        gm: GreetingMethodTyped[A]
      ): List[Greeting] = {
        getMethodMap(gm)
          .values
          .filter { greeting =>
            greeting.greetingMethod == gm &&
            (greeting.greetedId == id.map(_.value) || text.fold(false)(greeting.text.contains))
          }.toList
      }
    }
}
