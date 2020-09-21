package ru.otus.sc.game.dao

import ru.otus.sc.game.model.{Player, User}

trait GameCredentialsDao {

  /**
    * Получить список всех пользователей
    *
    * @return список всех пользователей
    */
  def allUsers(): List[User]

  /**
    * Получить пользователя по имени
    *
    * @param name искомое имя пользователя
    * @return пользователь
    */
  def user(name: String): Option[User]

  /**
    * Проверка занятого ника
    *
    * @param nick ник для проверки
    * @return занят или не занят
    */
  def nickBusy(nick: String): Boolean

  /**
    * Проверка ввода занятых учетных данных
    *
    * @param check метод проверки
    * @return учетные данные заняты или нет
    */
  def credentialBusy(check: User => Boolean): Boolean

  /**
    * Регистрация нового пользователя
    *
    * @param nick     ник игрока
    * @param username логин входа
    * @return успех или не успех
    */
  def registerUser(nick: String, username: String): Boolean

  /**
    * Получение игрока по идентификационным данным пользователя
    *
    * @param username имя пользователя
    * @return игрок, если существует
    */
  def player(username: String): Option[Player]
}
