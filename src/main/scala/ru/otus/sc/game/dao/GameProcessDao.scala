package ru.otus.sc.game.dao

import ru.otus.sc.game.model.GameProcessState._
import ru.otus.sc.game.model.{Direction, Player}

trait GameProcessDao {

  /**
    * Получение игрового состояния игрока
    *
    * @param player игрок
    * @return игровое состояние игрока
    */
  def gamerState(player: Player): GameProcessState

  /**
    * Игрок начинает новую игру.
    * Должна быть создана новая карта и размещены юниты
    *
    * @param player игрок
    */
  def newGame(player: Player): Boolean

  /**
    * Игрок заходит в игру.
    * Происходит поиск сохранения. Если сохранения нет - новая игра
    *
    * @param player игрок
    * @return
    */
  def signIn(player: Player): Boolean

  /**
    * Игрок заходит на карту.
    * После входа на карту - меняется меню на игровое.
    *
    * @param player игрок
    * @return смог или не смог зайти на карту
    */
  def enterMap(player: Player): Boolean

  /**
    * Загрузка игры
    *
    * @param player игрок
    * @return смог или не смог
    */
  def loadGame(player: Player): Boolean

  /**
    * Сохранение игры
    *
    * @param player игрок
    * @return смог или не смог
    */
  def saveGame(player: Player): Boolean

  /**
    * Показать карту
    *
    * @param player игрок
    * @return карта в виде строки
    */
  def showMap(player: Player): String

  /**
    * Показать врагов рядом с позицией игрока
    *
    * @param player игрок
    * @return враги вокруг
    */
  def showEnemies(player: Player): String

  /**
    * Игрок двигается по карте в направлении
    *
    * @param player    игрок
    * @param direction направление движения
    * @return смог или не смог
    */
  def move(player: Player, direction: Direction): Boolean

  /**
    * Активировать, если есть, предмет в позиции игрока
    *
    * @param player игрок
    * @return описание того, что произошло с игроком
    */
  def activateEntity(player: Player): String

  /**
    * Атаковать в определенном направлении
    *
    * @param player    игрок
    * @param direction направление атаки
    * @return результат атаки
    */
  def attackDirection(player: Player, direction: Direction): String

  /**
    * Выйти из карты в главное меню
    *
    * @param player игрок
    * @return
    */
  def quitMap(player: Player): Boolean

  /**
    * Показать текущее состояние игрока
    *
    * @param player игрок
    * @return текущее состояние
    */
  def showCurrentState(player: Player): String
}
