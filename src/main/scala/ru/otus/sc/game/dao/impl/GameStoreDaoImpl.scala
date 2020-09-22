package ru.otus.sc.game.dao.impl

import ru.otus.sc.game.dao.GameStoreDao
import ru.otus.sc.game.model._

import scala.collection.mutable

/**
  * Сохранение игровых данных
  */
class GameStoreDaoImpl extends GameStoreDao {
  private val saves: mutable.Map[String, PlayerCurrentGameState] = mutable.Map.empty

  /**
    * Полное сохранение
    *
    * @param player игрок
    * @param map    карта игрока
    * @param pos    последняя позиция
    * @return успех или не успех
    */
  override def save(player: Player, map: Map[Position, GameEntity], pos: Position): Boolean = {
    this.saves(player.nick) = PlayerCurrentGameState(player, GameProcessState.NONE, map, pos)
    true
  }

  /**
    * Сохраняем игрока и его последнюю позицию на карте
    *
    * @param player игрок
    * @param pos    позиция на карте
    * @return удалось или не удалось сохранить
    */
  override def savePlayer(player: Player, pos: Position): Boolean = {
    if (this.saves.contains(player.nick)) {
      this.saves(player.nick) = this.saves(player.nick).copy(player = player, pos = pos)
    } else {
      this.saves(player.nick) =
        PlayerCurrentGameState(player, GameProcessState.NONE, Map.empty[Position, GameEntity], pos)
    }
    true
  }

  /**
    * Загружаем данные игрока
    *
    * @param player игрок
    * @return сохраненный игрок и его позиция или ничего
    */
  override def loadPlayer(player: Player): Option[(Player, Position)] = {
    if (this.saves.contains(player.nick))
      Some((this.saves(player.nick).player, this.saves(player.nick).pos))
    else
      None
  }

  /**
    * Сохраняем карту
    *
    * @param player игрок
    * @param map    карта
    * @return удалось или не удалось
    */
  override def saveMap(player: Player, map: Map[Position, GameEntity]): Boolean = {
    if (this.saves.contains(player.nick)) {
      this.saves(player.nick) = this.saves(player.nick).copy(map = map)
    } else {
      this.saves(player.nick) =
        PlayerCurrentGameState(player, GameProcessState.NONE, map, Position(0, 0))
    }
    true
  }

  /**
    * Загружаем карту
    *
    * @param player игрок
    * @return сохраненная карта игрока или ничего
    */
  override def loadMap(player: Player): Option[Map[Position, GameEntity]] =
    if (this.saves.contains(player.nick))
      Some(this.saves(player.nick).map)
    else
      None
}
