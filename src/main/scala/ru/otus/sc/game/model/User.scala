package ru.otus.sc.game.model

/**
  * Пользователь, под которым игрок заходит в игру
  *
  * @param nick     никнейм, который отображается в игре
  * @param username имя пользователь
  */
case class User(nick: String, username: String)
