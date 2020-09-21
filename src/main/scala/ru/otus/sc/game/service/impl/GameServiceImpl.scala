package ru.otus.sc.game.service.impl

import java.util.UUID

import ru.otus.sc.game.dao.{GameCredentialsDao, GameProcessDao}
import ru.otus.sc.game.model.GameProcessState._
import ru.otus.sc.game.model.Player
import ru.otus.sc.game.service._

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class GameServiceImpl(gpd: GameProcessDao, gcd: GameCredentialsDao) extends GameService {
  private val connected: mutable.Map[String, Player] = mutable.Map.empty
  private val onMap: mutable.ListBuffer[String]      = new ListBuffer[String]()

  /**
    * Показать игровое меню
    * Игровое меню показывается для каждой стадии игры свое.
    * Например,
    * Незарегистрированный или не вошедший в игру игрок:
    * - Регистрация
    * - Войти
    * - Выход
    * Вошедший в игру игрок:
    * - Загрузить игру
    * - Начать новую игру
    * - Выйти из игры
    * Вошедший на карту игрок:
    * - Посмотреть врагов рядом
    * - Пойти в сторону
    * - Атаковать позицию
    * - Сохранить игру
    * - Выйти из карты
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
        List("signUp", "signIn", "quitGame")
      )
    } else {
      this.connected.get(request.id) match {
        case Some(player) =>
          this.gpd.gamerState(player) match {
            // состояние игрока неидентифицировано
            case NONE =>
              GameMenuResponse(
                List("signUp", "signIn", "quitGame")
              )
            // главное меню игры
            case MAIN_MENU =>
              GameMenuResponse(
                List(
                  "enterGame",
                  "loadGame",
                  "newGame",
                  "quitGame"
                )
              )
            // игрок в игровом меню или на игровой карте
            case GAME =>
              GameMenuResponse(
                List(
                  "showEnemies",
                  "move",
                  "attackPos",
                  "saveGame",
                  "loadGame",
                  "quitMap"
                )
              )
            // игрок проиграл или выиграл
            case LOSE | WIN =>
              GameMenuResponse(
                List("newGame", "showMap", "quitGame")
              )
          }
        // если игрока нет среди залогиненных
        case None =>
          GameMenuResponse(
            List("signUp", "signIn", "quitGame")
          )
      }
    }
  }

  /**
    * Регистрация нового игрока
    *
    * @param request информация для регистрации
    * @return Ответ об успешности или нет
    */
  override def signUp(request: GameSignUpRequest): GameSignUpResponse = {
    if (this.gcd.registerUser(request.nick, request.user))
      GameSignUpResponse(success = true)
    else
      GameSignUpResponse(success = false, "Nick or username are already busy")
  }

  /**
    * Вход в игру
    *
    * @param request информация для входа в игру
    * @return Ответ об успешности или нет
    */
  override def signIn(request: GameSignInRequest): GameSignInResponse = {
    this.gcd.player(request.user) match {
      case Some(player) =>
        this.connected.find(u => u._2 == player) match {
          case Some((uuid, _)) =>
            GameSignInResponse(success = true, uuid)
          case None =>
            val uuid = UUID.randomUUID().toString
            this.connected.put(uuid, player)
            this.gpd.signIn(player)
            GameSignInResponse(success = true, uuid)
        }
      case None =>
        GameSignInResponse(success = false, "Player with username doesn't exists")
    }
  }

  /**
    * Вход в игровую карту
    *
    * @param request запрос логин для входа
    * @return успех или не успех процесса с ошибкой
    */
  override def enterGame(request: EnterGameRequest): EnterGameResponse = {
    this.connected.get(request.uuid) match {
      case Some(player) =>
        if (this.gpd.gamerState(player) == MAIN_MENU) {
          this.onMap += player.nick
          this.gpd.enterMap(player)
          EnterGameResponse(success = true)
        } else {
          EnterGameResponse(success = false, "Player already in game")
        }
      case None =>
        EnterGameResponse(
          success = false,
          "Need to be logged in before send this request [use 'signIn']"
        )
    }
  }

  /**
    * Загрузка игры
    * Если игрок сохранял игру, то она загрузится
    * Загружается сохраненный игрок и карта
    *
    * @param request запрос на загрузку игры
    * @return успех или не успех процесса с ошибкой
    */
  override def loadGame(request: LoadGameRequest): LoadGameResponse =
    this.connected.get(request.uuid) match {
      case Some(player) =>
        if (List(MAIN_MENU, GAME).contains(this.gpd.gamerState(player))) {
          if (this.gpd.loadGame(player)) {
            LoadGameResponse(success = true)
          } else {
            LoadGameResponse(success = false, data = "Wrong in load game. Contact the developer")
          }
        } else {
          LoadGameResponse(
            success = false,
            data =
              "Wrong request. On this player game state you can't load game [available commands 'showGameMenu']"
          )
        }
      case None =>
        LoadGameResponse(
          success = false,
          data = "Need to be logged in before send this request [use 'signIn']"
        )
    }

  /**
    * Сохранение игры
    * Текущая игра сохраняется, статы игрока тоже сохраняются
    *
    * @param request запрос на сохранение игры
    * @return успех или не успех процесса с ошибкой
    */
  override def saveGame(request: SaveGameRequest): SaveGameResponse = {
    this.connected.get(request.uuid) match {
      case Some(player) =>
        if (this.gpd.gamerState(player) == GAME) {
          if (this.gpd.saveGame(player)) {
            SaveGameResponse(success = true)
          } else {
            SaveGameResponse(
              success = false,
              data = "Wrong in save game. Contact the developer"
            )
          }
        } else {
          SaveGameResponse(
            success = false,
            data =
              "Wrong request. On this player game state you can't save game [available commands 'showGameMenu']"
          )
        }
      case None =>
        SaveGameResponse(
          success = false,
          data = "Need to be logged in before send this request [use 'signIn']"
        )
    }
  }

  /**
    * Начать новую игру
    * Текущая игра сбрасывается и начинается новая.
    * Карта перегенерируется тоже
    *
    * @param request запрос на новую игру
    * @return успех или не успех процесса с ошибкой
    */
  override def newGame(request: NewGameRequest): NewGameResponse = {
    this.connected.get(request.uuid) match {
      case Some(player) =>
        if (List(MAIN_MENU, LOSE, WIN).contains(this.gpd.gamerState(player))) {
          if (this.gpd.newGame(player)) {
            NewGameResponse(success = true)
          } else {
            NewGameResponse(
              success = false,
              data = "Wrong in start new game. Contact the developer"
            )
          }
        } else {
          NewGameResponse(
            success = false,
            data =
              "Wrong request. On this player game state you can't start new game [available commands 'showGameMenu']"
          )
        }
      case None =>
        NewGameResponse(
          success = false,
          "Need to be logged in before send this request [use 'signIn']"
        )
    }
  }

  /**
    * Показать карту
    * Доступно только, когда игра выиграна или проиграна
    *
    * @param request запрос на показ карты игры
    * @return успех или не успех процесса с ошибкой
    */
  override def showMap(request: ShowMapRequest): ShowMapResponse = {
    this.connected.get(request.uuid) match {
      case Some(player) =>
        if (List(LOSE, WIN).contains(this.gpd.gamerState(player))) {
          ShowMapResponse(success = true, data = this.gpd.showMap(player))
        } else {
          ShowMapResponse(
            success = false,
            data =
              "Wrong request. On this player game state you can't show map [available commands 'showGameMenu']"
          )
        }
      case None =>
        ShowMapResponse(
          success = false,
          data = "Need to be logged in before send this request [use 'signIn']"
        )
    }
  }

  /**
    * Показать врагов рядом
    * Доступно в игре. Показывает врагов на расстоянии 1 шага по всем направлениям
    *
    * @param request запрос на показать врагов рядом
    * @return успех или не успех процесса с ошибкой
    */
  override def showEnemies(request: ShowEnemiesRequest): ShowEnemiesResponse = {
    this.connected.get(request.uuid) match {
      case Some(player) =>
        if (this.gpd.gamerState(player) == GAME) {
          ShowEnemiesResponse(success = true, data = this.gpd.showEnemies(player))
        } else {
          ShowEnemiesResponse(
            success = false,
            data =
              "Wrong request. On this player game state you can't show enemies [available commands 'showGameMenu']"
          )
        }
      case None =>
        ShowEnemiesResponse(
          success = false,
          data = "Need to be logged in before send this request [use 'signIn']"
        )
    }
  }

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
  override def move(request: MoveRequest): MoveResponse = {
    this.connected.get(request.uuid) match {
      case Some(player) =>
        if (this.gpd.gamerState(player) == GAME) {
          if (this.gpd.move(player, request.direction)) {
            MoveResponse(
              success = true,
              data = this.gpd.activateEntity(player)
            )
          } else {
            MoveResponse(
              success = false,
              data = "the cell is occupied by the enemy"
            )
          }
        } else {
          MoveResponse(
            success = false,
            data =
              "Wrong request. On this player game state you can't move on the game map [available commands 'showGameMenu']"
          )
        }
      case None =>
        MoveResponse(
          success = false,
          data = "Need to be logged in before send this request [use 'signIn']"
        )
    }
  }

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
  override def attackPos(request: AttackPosRequest): AttackPosResponse = {
    this.connected.get(request.uuid) match {
      case Some(player) =>
        if (this.gpd.gamerState(player) == GAME) {
          AttackPosResponse(
            success = true,
            data = this.gpd.attackDirection(player, request.direction)
          )
        } else {
          AttackPosResponse(
            success = false,
            data =
              "Wrong request. On this player game state you can't attack [available commands 'showGameMenu']"
          )
        }
      case None =>
        AttackPosResponse(
          success = false,
          data = "Need to be logged in before send this request [use 'signIn']"
        )
    }
  }

  /**
    * Выход из игровой карты
    *
    * @param request запрос на выход из игровой карты
    */
  override def quitGameMap(request: QuitGameMapRequest): QuitGameMapResponse = {
    this.connected.get(request.uuid) match {
      case Some(player) =>
        if (this.gpd.gamerState(player) == GAME) {
          if (this.gpd.quitMap(player)) {
            QuitGameMapResponse(success = true)
          } else {
            QuitGameMapResponse(
              success = false,
              data = "Wrong in quit from the game map. Contact the developer"
            )
          }
        } else {
          QuitGameMapResponse(
            success = false,
            data =
              "Wrong request. On this player game state you can't quit from the game map [available commands 'showGameMenu']"
          )
        }
      case None =>
        QuitGameMapResponse(
          success = false,
          data = "Need to be logged in before send this request [use 'signIn']"
        )
    }
  }

  /**
    * Выход из игры
    *
    * @param request запрос на выход из игры
    */
  override def quitGame(request: QuitGameRequest): Unit = {
    System.exit(0)
  }

  /**
    * Показать текущее игровое состояние игрока
    *
    * @return
    */
  override def showGameState(request: GameCurrentStateRequest): GameCurrentStateResponse =
    this.connected.get(request.uuid) match {
      case Some(player) =>
        GameCurrentStateResponse(this.gpd.showCurrentState(player))
      case None =>
        GameCurrentStateResponse("no player found")
    }
}
