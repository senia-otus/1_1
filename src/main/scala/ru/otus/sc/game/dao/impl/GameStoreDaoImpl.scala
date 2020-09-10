package ru.otus.sc.game.dao.impl

import ru.otus.sc.game.dao.GameStoreDao
import ru.otus.sc.game.model.{Entity, Player, PlayerStore, Position}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class GameStoreDaoImpl extends GameStoreDao {
  private val store = new ListBuffer[PlayerStore]

  /**
    * Сохраняем игрока и его последнюю позицию на карте
    *
    * @param player игрок
    * @param pos    позиция на карте
    * @return удалось или не удалось сохранить
    */
  override def savePlayer(player: Player, pos: Position): Boolean = {
    val storeCell = PlayerStore(player, pos)

    try {
      this.store -= storeCell
      this.store += storeCell
      true
    } catch {
      case ex: Exception => {
        println(s"Возникла ошибка: $ex")
        ex.printStackTrace()
        false
      }
    }
  }

  /**
    * Загружаем данные игрока
    *
    * @param player игрок
    * @return хранимая информация по игроку
    */
  override def loadPlayer(player: Player): Option[PlayerStore] = {
    this.store.find(ps => ps.player.nick == player.nick)
  }

  /**
    * Сохраняем карту
    *
    * @param map карта
    * @return удалось или не удалось
    */
  override def saveMap(map: Map[Position, Entity]): Boolean = {
    true
  }

  /**
    * Загружаем карту
    *
    * @return карта
    */
  override def loadMap(): Map[Position, Entity] = {
    Map.empty
  }
}
