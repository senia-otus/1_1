package ru.otus.sc.greet.dao

import java.util.concurrent.atomic.AtomicInteger

import ru.otus.sc.greet.model.{ Id, User }

import scala.annotation.tailrec
import scala.collection.concurrent.TrieMap

/**
 * Dao для хранения пользователей
 */
trait UserDao {
  /**
   * Создание пользователя
   */
  def create(user: User): User

  /**
   * Получение пользователя
   */
  def find(id: Id[User]): Option[User]

  /**
   * Поиск подчинённых пользователя
   */
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
        //поис подчинённых по списку менеджеров
        @tailrec def search(managers: Set[Id[User]], subordinates: Set[User]): Set[User] = {
          if (managers.isEmpty) {
            //менеджеров нет - возвращаем найденных подчинённых
            subordinates
          } else {
            //ищем подчиненных переданных менеджеров
            val foundSubordinates = users.values.filter(user => user.managerId.fold(false)(managers.contains))
            //сохраняем найденных подчинённых в subordinates, и передаём их как менеджеров в новую итерацию поиска
            search(foundSubordinates.flatMap(_.id).toSet, subordinates ++ foundSubordinates)
          }
        }
        search(managers = Set(id), subordinates = Set.empty)
      }
    }
}
