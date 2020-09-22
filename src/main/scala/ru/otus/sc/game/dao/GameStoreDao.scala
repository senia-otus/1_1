package ru.otus.sc.game.dao

import ru.otus.sc.game.model.{GameEntity, Player, Position}

trait GameStoreDao {

  /**
    * Полное сохранение
    *
    * @param player игрок
    * @param map    карта игрока
    * @param pos    последняя позиция
    * @return успех или не успех
    */
  def save(player: Player, map: Map[Position, GameEntity], pos: Position): Boolean

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
    * @return сохраненный игрок и его позиция
    */
  def loadPlayer(player: Player): Option[(Player, Position)]

  /**
    * Сохраняем карту
    *
    * @param player игрок
    * @param map    карта
    * @return удалось или не удалось
    */
  def saveMap(player: Player, map: Map[Position, GameEntity]): Boolean

  /**
    * Загружаем карту
    *
    * @param player игрок
    * @return карта
    */
  def loadMap(player: Player): Option[Map[Position, GameEntity]]
}
