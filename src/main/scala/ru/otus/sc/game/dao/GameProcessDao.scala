package ru.otus.sc.game.dao

import ru.otus.sc.game.model.{Player, Position}

trait GameProcessDao {

  /**
    * Игрок начинает новую игру.
    * Должна быть создана новая карта и размещены юниты
    *
    * @param player игрок
    */
  def newGame(player: Player): Position

  /**
    * Игрок заходит в игру.
    * Надо сделать поиск сохранения и предложить пользователю выбор
    *
    * @param player игрок
    * @return
    */
  def enterGame(player: Player): Position

  /**
    * Загружаем игрока на карте
    *
    * @param player игрок
    * @return игрок
    */
  def loadPlayer(player: Player): Player
}
