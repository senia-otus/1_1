package ru.otus.sc.game.model

/**
  * Игровое состояние игрока
  *
  * NONE - игрока пока нет нигде
  * MAIN_MENU - игрок находиться в главном меню
  * GAME - игрок находится на игровой карте
  * LOSE - игрок проиграл
  * WIN - игрок выиграл
  */
object GameProcessState extends Enumeration {
  type GameProcessState = Value
  val NONE, MAIN_MENU, GAME, LOSE, WIN = Value
}
