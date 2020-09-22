package ru.otus.sc.game.model

/**
  * Игровой запрос
  */
trait GameRequest {}

/**
  * Запрос показа игрового меню
  *
  * @param id если есть, то присвоенный идентификатор пользователя
  */
case class ShowGameMenuRequest(id: String = "") extends GameRequest

/**
  * Запрос регистрации нового пользователя
  *
  * @param nick ник игрока
  * @param user имя пользователя
  */
case class SignUpRequest(nick: String, user: String) extends GameRequest

/**
  * Запрос входа в игру пользователя
  *
  * @param user имя пользователя
  */
case class SignInRequest(user: String) extends GameRequest

/**
  * Запрос входа в игровую карту
  *
  * @param uuid идентификатор пользователя
  */
case class EnterGameMapRequest(uuid: String) extends GameRequest

/**
  * Запрос загрузки игры
  *
  * @param uuid идентификатор пользователя
  */
case class LoadGameRequest(uuid: String) extends GameRequest

/**
  * Запрос сохранения игры
  *
  * @param uuid идентификатор пользователя
  */
case class SaveGameRequest(uuid: String) extends GameRequest

/**
  * Запрос новой игры
  *
  * @param uuid идентификатор пользователя
  */
case class NewGameRequest(uuid: String) extends GameRequest

/**
  * Запрос показа карты
  *
  * @param uuid идентификатор пользователя
  */
case class ShowMapRequest(uuid: String) extends GameRequest

/**
  * Запрос показа противников рядом
  *
  * @param uuid идентификатор пользователя
  */
case class ShowEnemiesRequest(uuid: String) extends GameRequest

/**
  * Запрос на передвижение по карте в направлении
  *
  * @param uuid      идентификатор пользователя
  * @param direction направление движения
  */
case class MoveRequest(uuid: String, direction: Direction) extends GameRequest

/**
  * Запрос на атаку в указанном направлении
  *
  * @param uuid      идентификатор пользователя
  * @param direction направление атаки
  */
case class AttackDirectionRequest(uuid: String, direction: Direction) extends GameRequest

/**
  * Запрос на выход из игровой карты
  *
  * @param uuid идентификатор пользователя
  */
case class QuitGameMapRequest(uuid: String) extends GameRequest

/**
  * Запрос на выход из игры
  */
case class QuitGameRequest() extends GameRequest

/**
  * Отладочный запрос вывода игрового состояния игрока
  *
  * @param uuid идентификатор пользователя
  */
case class GameCurrentStateRequest(uuid: String) extends GameRequest
