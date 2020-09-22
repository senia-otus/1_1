package ru.otus.sc.game.service

import ru.otus.sc.game.model._

trait GameService {

  /**
    * Показать игровое меню
    * Игровое меню показывается для каждой стадии игры свое.
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
  def showGameMenu(request: ShowGameMenuRequest): ShowGameMenuResponse

  /**
    * Регистрация нового игрока
    *
    * @param request информация для регистрации
    * @return Ответ об успешности или нет
    */
  def signUp(request: SignUpRequest): SignUpResponse

  /**
    * Вход в игру
    *
    * @param request информация для входа в игру
    * @return Ответ об успешности или нет
    */
  def signIn(request: SignInRequest): SignInResponse

  /**
    * Вход в игровую карту
    *
    * @param request запрос логин для входа
    * @return успех или не успех процесса с ошибкой
    */
  def enterGame(request: EnterGameMapRequest): EnterGameMapResponse

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
  def newGame(request: NewGameRequest): NewGameResponse

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
  def attackPos(request: AttackDirectionRequest): AttackDirectionResponse

  /**
    * Выход из игровой карты
    *
    * @param request запрос на выход из игровой карты
    */
  def quitGameMap(request: QuitGameMapRequest): QuitGameMapResponse

  /**
    * Выход из игры
    *
    * @param request запрос на выход из игры
    */
  def quitGame(request: QuitGameRequest): Unit

  /**
    * Показать текущее игровое состояние игрока
    *
    * @return
    */
  def showGameState(request: GameCurrentStateRequest): GameCurrentStateResponse
}
