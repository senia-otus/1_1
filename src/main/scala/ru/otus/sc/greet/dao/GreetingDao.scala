package ru.otus.sc.greet.dao

import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicInteger

import ru.otus.sc.greet.dao.GreetingDao.MapConstraint
import ru.otus.sc.greet.dao.GreetingDao.MapConstraint.GreetingMethodMap
import ru.otus.sc.greet.model.GreetingMethod.{ BotGreetingMethod, GuestGreetingMethod, UserGreetingMethod }
import ru.otus.sc.greet.model._
import shapeless.HMap

import scala.collection.concurrent.TrieMap

trait GreetingDao {
  def greetingPrefix: String
  def greetingPostfix: String
  def greet[A](someone: A)(implicit
    gm: GreetingMethod[A],
    mc: MapConstraint[GreetingMethod[A], GreetingMethodMap[A]]
  ): Greeting[A]

  def findGreetings[A](id: Id[A])(implicit
    gm: GreetingMethod[A],
    mc: MapConstraint[GreetingMethod[A], GreetingMethodMap[A]]
  ): List[Greeting[A]]
}

object GreetingDao {
  case class MapConstraint[-K, V]()
  object MapConstraint {
    type GreetingMethodMap[A] = TrieMap[Id[Greeting[A]], Greeting[A]]
    implicit lazy val userMap  = new MapConstraint[GreetingMethod[User], GreetingMethodMap[User]]
    implicit lazy val guestMap = new MapConstraint[GreetingMethod[Guest], GreetingMethodMap[Guest]]
    implicit lazy val botMap   = new MapConstraint[GreetingMethod[Bot], GreetingMethodMap[Bot]]
  }

  def methodsMap: HMap[MapConstraint] = {
    GreetingMethod.values.foldRight(new HMap[MapConstraint]) { (gm, map) =>
      gm match {
        case method @ UserGreetingMethod  => map + (method -> TrieMap.empty[Id[Greeting[User]], Greeting[User]])
        case method @ GuestGreetingMethod => map + (method -> TrieMap.empty[Id[Greeting[Guest]], Greeting[Guest]])
        case method @ BotGreetingMethod   => map + (method -> TrieMap.empty[Id[Greeting[Bot]], Greeting[Bot]])
      }
    }
  }

  def inmemory: GreetingDao =
    new GreetingDao {
      private val counter                  = new AtomicInteger
      private val greetings                = GreetingDao.methodsMap
      override val greetingPrefix: String  = "Hi, "
      override val greetingPostfix: String = "!"

      private def getMethodMap[A](
        gm: GreetingMethod[A],
        mc: MapConstraint[GreetingMethod[A], GreetingMethodMap[A]]
      ): GreetingMethodMap[A] = {
        greetings
          .get(gm)(mc)
          .getOrElse(throw new RuntimeException(s"Unregistered greeting method: $gm. Should never happen"))
      }

      override def greet[A](someone: A)(implicit
        gm: GreetingMethod[A],
        mc: MapConstraint[GreetingMethod[A], GreetingMethodMap[A]]
      ): Greeting[A] = {
        val id       = Id(counter.incrementAndGet())
        val greeting = Greeting(
          Some(id),
          gm.id(someone),
          gm,
          s"$greetingPrefix ${gm.greet(someone)} $greetingPostfix",
          LocalDateTime.now
        )
        getMethodMap(gm, mc).update(id, greeting)
        greeting
      }

      override def findGreetings[A](id: Id[A])(implicit
        gm: GreetingMethod[A],
        mc: MapConstraint[GreetingMethod[A], GreetingMethodMap[A]]
      ): List[Greeting[A]] = {
        getMethodMap(gm, mc)
          .values
          .filter { greeting =>
            greeting.greetingMethod == gm &&
            greeting.greetedId.contains(id)
          }.toList
      }
    }
}
