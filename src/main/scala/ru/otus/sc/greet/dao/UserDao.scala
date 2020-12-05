package ru.otus.sc.greet.dao

import java.util.concurrent.atomic.AtomicInteger

import ru.otus.sc.greet.model.{ Id, User }

import scala.annotation.tailrec
import scala.collection.concurrent.TrieMap

trait UserDao {
  def create(user: User): User
  def find(id: Id[User]): Option[User]
  def findSubordinates(id: Id[User]): Set[User]
}

object UserDao {
  def inmemory: UserDao =
    new UserDao {
      private val counter = new AtomicInteger
      private val users   = TrieMap.empty[Id[User], User]

      override def create(user: User): User = {
        val id         = Id(counter.incrementAndGet)
        val userWithId = user.copy(id = Some(id))
        users.addOne(id, userWithId)
        userWithId
      }

      override def find(id: Id[User]): Option[User] = {
        users.get(id)
      }

      override def findSubordinates(id: Id[User]): Set[User] = {
        @tailrec def search(managers: Set[Id[User]], subordinates: Set[User]): Set[User] = {
          if (managers.isEmpty) {
            subordinates
          } else {
            val foundSubordinates = users.values.filter(user => user.managerId.fold(false)(managers.contains))
            search(foundSubordinates.flatMap(_.id).toSet, subordinates ++ foundSubordinates)
          }
        }
        search(managers = Set(id), subordinates = Set.empty)
      }
    }
}
