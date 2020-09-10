package ru.otus.sc.game.service

import ru.otus.sc.game.model.User

trait GameService {

  /**
    * Вход в игру
    *
    * @param request запрос логин/пароль для входа
    * @return успех или не успех процесса с ошибкой
    */
  def enterGame(request: GameEnterRequest): GameEnterResponse

  /**
    * Регистрация в игре
    *
    * @param request запрос на регистрацию
    * @return успех или не успех процесса с ошибкой
    */
  def startNewGame(request: GameRegisterRequest): GameRegisterResponse

  /**
    * Получить список игроков
    *
    * @return список игроков
    */
  def listOfUsers(): GameListUsersResponse

}

case class GameEnterRequest(user: String, pass: String)
case class GameEnterResponse(success: Boolean, error: String = "")

case class GameRegisterRequest(nick: String, user: String, pass: String)
case class GameRegisterResponse(success: Boolean, error: String = "")

case class GameListUsersResponse(users: List[User])
