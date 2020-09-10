package ru.otus.sc.game.dao

import ru.otus.sc.game.model.{Entity, Player, PlayerStore, Position}

trait GameStoreDao {

  /**
    * Сохраняем игрока и его последнюю позицию на карте
    *
    * @param player игрок
    * @param pos    позиция на карте
    * @return удалось или не удалось сохранить
    */
  def savePlayer(player: Player, pos: Position): Boolean

  /**
    * Загружаем данные игрока
    *
    * @param player игрок
    * @return хранимая информация по игроку
    */
  def loadPlayer(player: Player): Option[PlayerStore]

  /**
    * Сохраняем карту
    *
    * @param map карта
    * @return удалось или не удалось
    */
  def saveMap(map: Map[Position, Entity]): Boolean

  /**
    * Загружаем карту
    *
    * @return карта
    */
  def loadMap(): Map[Position, Entity]
}
