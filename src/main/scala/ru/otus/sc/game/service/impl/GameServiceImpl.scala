package ru.otus.sc.game.service.impl

import ru.otus.sc.game.dao.GameCredentialsDao
import ru.otus.sc.game.service._

class GameServiceImpl(dao: GameCredentialsDao) extends GameService {

  /**
    * Вход в игру
    *
    * @param request запрос логин/пароль для входа
    * @return успех или не успех процесса с ошибкой
    */
  override def enterGame(request: GameEnterRequest): GameEnterResponse = {
    val user = dao.user(request.user)
    if (user.isDefined && user.get.password == request.pass) {
      GameEnterResponse(success = true)
    } else
      GameEnterResponse(success = false, error = "Неправильный логин или пароль")
  }

  /**
    * Регистрация в игре
    *
    * @param request запрос на регистрацию
    * @return успех или не успех процесса с ошибкой
    */
  override def startNewGame(request: GameRegisterRequest): GameRegisterResponse = {
    if (dao.credentialBusy(u => u.nick == request.nick)) {
      GameRegisterResponse(
        success = false,
        error = s"""Ник [${request.nick}] уже занят другим пользователем"""
      )
    } else if (dao.credentialBusy(u => u.username == request.user)) {
      GameRegisterResponse(
        success = false,
        error = s"""Имя пользователя [${request.nick}] уже занято другим пользователем"""
      )
    } else {
      dao.registerUser(request.nick, request.user, request.pass)
      GameRegisterResponse(success = true)
    }
  }

  /**
    * Получить список игроков
    *
    * @return список игроков
    */
  def listOfUsers(): GameListUsersResponse =
    GameListUsersResponse(dao.allUsers())
}
