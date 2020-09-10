package ru.otus.sc.game.service

trait GameService {

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
    * - Сохранить игру
    * - Начать новую игру
    * - Выйти из учетной записи
    * В игре когда еще не умер:
    * - Посмотреть врагов рядом
    * - Пойти в сторону
    * - Атаковать позицию
    * - Выйти в главное меню
    * В игре когда умер:
    * - Показать карту
    * - Выйти в главное меню
    *
    * @param request запрос с айдишником для залогинненного игрока
    * @return список доступных операций
    */
  def showGameMenu(request: GameMenuRequest): GameMenuResponse

  /**
    * Регистрация нового игрока
    *
    * @param request информация для регистрации
    * @return Ответ об успешности или нет
    */
  def registerNewPlayer(request: GameRegisterRequest): GameRegisterResponse

  /**
    * Вход в игру
    *
    * @param request запрос логин для входа
    * @return успех или не успех процесса с ошибкой
    */
  def enterGame(request: EnterGameRequest): EnterGameResponse

  /**
    * Загрузка игры
    * Если игрок сохранял игру, то она загрузится
    *
    * @param request запрос на загрузку игры
    * @return успех или не успех процесса с ошибкой
    */
  def loadGame(request: LoadGameRequest): LoadGameResponse

  /**
    * Начать новую игру
    * Текущая игра сбрасывается и начинается новая.
    * Карта перегенерируется тоже
    *
    * @param request запрос на новую игру
    * @return успех или не успех процесса с ошибкой
    */
  def startNewGame(request: StartNewGameRequest): StartNewGameResponse

  /**
    * Сохранение игры
    * Текущая игра сохраняется, статы игрока тоже сохраняются
    *
    * @param request запрос на сохранение игры
    * @return успех или не успех процесса с ошибкой
    */
  def saveGame(request: SaveGameRequest): SaveGameResponse

  /**
    * Показать карту
    * Доступно только, когда игра выиграна или проиграна
    *
    * @param request запрос на показ карты игры
    * @return успех или не успех процесса с ошибкой
    */
  def showMap(request: ShowMapRequest): ShowMapResponse

  /**
    * Показать врагов рядом
    * Доступно в игре. Показывает врагов на расстоянии 1 шага по всем направлениям
    *
    * @param request запрос на показать врагов рядом
    * @return успех или не успех процесса с ошибкой
    */
  def showEnemies(request: ShowEnemiesRequest): ShowEnemiesResponse

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
  def move(request: MoveRequest): MoveResponse

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
  def attackPos(request: AttackPosRequest): AttackPosResponse

  /**
    * Выход из игры
    *
    * @param request запрос на выход из игры
    */
  def quitGame(request: QuitGameRequest): Unit
}

case class GameMenuRequest(id: String = "")
case class GameMenuResponse(availableMenuItems: List[String])

case class GameRegisterRequest(nick: String, user: String)
case class GameRegisterResponse(success: Boolean, error: String = "")

case class EnterGameRequest(user: String)
case class EnterGameResponse(success: Boolean, error: String = "")

case class LoadGameRequest()
case class LoadGameResponse()

case class StartNewGameRequest(nick: String)
case class StartNewGameResponse()

case class SaveGameRequest()
case class SaveGameResponse()

case class ShowMapRequest()
case class ShowMapResponse()

case class ShowEnemiesRequest()
case class ShowEnemiesResponse()

case class MoveRequest()
case class MoveResponse()

case class AttackPosRequest()
case class AttackPosResponse()

case class QuitGameRequest()
