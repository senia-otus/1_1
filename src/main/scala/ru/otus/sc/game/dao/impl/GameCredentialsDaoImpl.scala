package ru.otus.sc.game.dao.impl

import ru.otus.sc.game.dao.GameCredentialsDao
import ru.otus.sc.game.model.{Player, PlayerStats, User}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class GameCredentialsDaoImpl extends GameCredentialsDao {
  private val users: mutable.ListBuffer[User] = new ListBuffer[User]
  this.users += User("Guest", "guest")

  /**
    * Получить список всех пользователей
    *
    * @return список всех пользователей
    */
  override def allUsers(): List[User] =
    this.users.toList

  /**
    * Получить пользователя по имени
    *
    * @param name искомое имя пользователя
    * @return пользователь
    */
  override def user(name: String): Option[User] =
    this.users.find(u => u.username == name)

  /**
    * Проверка занятого ника
    *
    * @param nick ник для проверки
    * @return занят или не занят
    */
  override def nickBusy(nick: String): Boolean =
    this.users.exists(u => u.nick == nick)

  /**
    * Проверка ввода занятых учетных данных
    *
    * @param check метод проверки
    * @return учетные данные заняты или нет
    */
  override def credentialBusy(check: User => Boolean): Boolean =
    this.users.exists(u => check(u))

  /**
    * Регистрация нового пользователя
    *
    * @param nick     ник игрока
    * @param username логин входа
    * @return созданный пользователь
    */
  override def registerUser(nick: String, username: String): Boolean = {
    if (credentialBusy(u => u.nick == nick || u.username == username)) {
      false
    } else {
      this.users += User(nick, username)
      true
    }
  }

  /**
    * Получение игрока по идентификационным данным пользователя
    *
    * @param username имя пользователя
    * @return игрок, если существует
    */
  override def player(username: String): Option[Player] = {
    val user = this.users.find(u => u.username == username)
    if (user.isDefined) {
      Some(Player(user.get.nick, PlayerStats.maxHealth))
    } else {
      None
    }
  }
}
