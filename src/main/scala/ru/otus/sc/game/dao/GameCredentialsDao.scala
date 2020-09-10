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
    * @param nick ник игрока
    * @param username логин входа
    * @param password пароль входа
    * @return созданный пользователь
    */
  def registerUser(nick: String, username: String, password: String): Player
}
