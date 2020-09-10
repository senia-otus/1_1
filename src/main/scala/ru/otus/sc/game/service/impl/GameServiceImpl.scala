package ru.otus.sc.game.service.impl

import ru.otus.sc.game.dao.{GameCredentialsDao, GameProcessDao, GameStoreDao}
import ru.otus.sc.game.model.Player
import ru.otus.sc.game.service._

import scala.collection.mutable

class GameServiceImpl(gpd: GameProcessDao, gcd: GameCredentialsDao, gsd: GameStoreDao)
    extends GameService {
  private val enteredUsers: mutable.Map[String, Player] = mutable.Map.empty

  /**
    * Показать игровое меню
    * Игровое меню показывается для каждой стадии игры свое.
    * Например,
    * Незарегистрированный или не вошедший в игру игрок:
    * - Регистрация
    * - Войти
    * - Выход
    * Вошедший в игру игрок:
    * - Посмотреть врагов рядом
    * - Пойти в сторону
    * - Атаковать позицию
    * - Загрузить игру
    * - Сохранить игру
    * - Начать новую игру
    * - Выйти из игры
    * В игре когда умер:
    * - Показать карту
    * - Выйти из игры
    *
    * @param request запрос с айдишником для залогинненного игрока
    * @return список доступных операций
    */
  override def showGameMenu(request: GameMenuRequest): GameMenuResponse = {
    if (request.id.isEmpty) {
      GameMenuResponse(
        List("registerNewPlayer", "enterGame", "quitGame")
      )
    } else {
      val player = this.enteredUsers.get(request.id)
      if (player.isEmpty) {
        GameMenuResponse(
          List("registerNewPlayer", "enterGame", "quitGame")
        )
      } else {
        if (player.get.health == 0.0) {
          GameMenuResponse(
            List("showMap", "quitGame")
          )
        } else {
          GameMenuResponse(
            List(
              "showEnemies",
              "move",
              "attackPos",
              "loadGame",
              "saveGame",
              "startNewGame",
              "quitGame"
            )
          )
        }
      }
    }
  }

  /**
    * Регистрация нового игрока
    *
    * @param request информация для регистрации
    * @return Ответ об успешности или нет
    */
  override def registerNewPlayer(request: GameRegisterRequest): GameRegisterResponse = {
    if (gcd.registerUser(request.nick, request.user))
      GameRegisterResponse(success = true)
    else
      GameRegisterResponse(success = false, "Ник или имя пользователя уже заняты")
  }

  /**
    * Вход в игру
    *
    * @param request запрос логин для входа
    * @return успех или не успех процесса с ошибкой
    */
  override def enterGame(request: EnterGameRequest): EnterGameResponse = ???

  /**
    * Загрузка игры
    * Если игрок сохранял игру, то она загрузится
    *
    * @param request запрос на загрузку игры
    * @return успех или не успех процесса с ошибкой
    */
  override def loadGame(request: LoadGameRequest): LoadGameResponse = ???

  /**
    * Начать новую игру
    * Текущая игра сбрасывается и начинается новая.
    * Карта перегенерируется тоже
    *
    * @param request запрос на новую игру
    * @return успех или не успех процесса с ошибкой
    */
  override def startNewGame(request: StartNewGameRequest): StartNewGameResponse = ???

  /**
    * Сохранение игры
    * Текущая игра сохраняется, статы игрока тоже сохраняются
    *
    * @param request запрос на сохранение игры
    * @return успех или не успех процесса с ошибкой
    */
  override def saveGame(request: SaveGameRequest): SaveGameResponse = ???

  /**
    * Показать карту
    * Доступно только, когда игра выиграна или проиграна
    *
    * @param request запрос на показ карты игры
    * @return успех или не успех процесса с ошибкой
    */
  override def showMap(request: ShowMapRequest): ShowMapResponse = ???

  /**
    * Показать врагов рядом
    * Доступно в игре. Показывает врагов на расстоянии 1 шага по всем направлениям
    *
    * @param request запрос на показать врагов рядом
    * @return успех или не успех процесса с ошибкой
    */
  override def showEnemies(request: ShowEnemiesRequest): ShowEnemiesResponse = ???

  /**
    * Пойти в сторону
    * Игрок идет в выбранную стороную.
    * Возможные варианты:
    * - клетка занята врагом. тогда игрок не может туда пойти
    * - в клетке стоит сундук или ловушка. тогда автоматически откроется и игрок займет место в клетке
    * - клетка свободна. тогда игрок спокойно на нее встанет
    *
    * @param request запрос на пойти в указанном направлении
    * @return успех или не успех процесса с ошибкой
    */
  override def move(request: MoveRequest): MoveResponse = ???

  /**
    * Атаковать клетку
    * Игрок атакует клетку.
    * Возможные варианты:
    * - в клетке стоит враг. тогда начнется схватка. по итогу, либо враг погибнет, либо игрок
    * - в клетке ловушка. тогда ловушка исчезнет
    * - в клетке ничего. тогда ничего и не произойдет
    *
    * @param request запрос на атаку клетки
    * @return успех или не успех процесса с ошибкой
    */
  override def attackPos(request: AttackPosRequest): AttackPosResponse = ???

  /**
    * Выход из игры
    *
    * @param request запрос на выход из игры
    */
  override def quitGame(request: QuitGameRequest): Unit = ???
}
