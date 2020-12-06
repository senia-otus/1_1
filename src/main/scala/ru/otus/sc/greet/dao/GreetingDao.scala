package ru.otus.sc.greet.dao

import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicInteger

import ru.otus.sc.greet.GreetingConfig
import ru.otus.sc.greet.model.GreetingMethod.MapConstraint.GreetingMethodMap
import ru.otus.sc.greet.model._

trait GreetingDao {
  def greet[A](someone: A)(implicit gm: GreetingMethod[A]): Greeting[A]
  def findGreetings[A](id: Option[Id[A]], name: Option[String])(implicit gm: GreetingMethod[A]): List[Greeting[A]]
}

object GreetingDao {
  def inmemory(config: GreetingConfig): GreetingDao =
    new GreetingDao {
      private val counter   = new AtomicInteger
      private val greetings = GreetingMethod.toMap

      private def getMethodMap[A](gm: GreetingMethod[A]): GreetingMethodMap[A] = {
        greetings
          .get(gm)(gm.constraint)
          .getOrElse(throw new RuntimeException(s"Unregistered greeting method: $gm. Should never happen"))
      }

      override def greet[A](someone: A)(implicit gm: GreetingMethod[A]): Greeting[A] = {
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
        gm: GreetingMethod[A]
      ): List[Greeting[A]] = {
        getMethodMap(gm)
          .values
          .filter { greeting =>
            greeting.greetingMethod == gm &&
            (greeting.greetedId == id || text.fold(false)(greeting.text.contains))
          }.toList
      }
    }
}
