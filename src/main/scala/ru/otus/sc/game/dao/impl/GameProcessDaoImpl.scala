package ru.otus.sc.game.dao.impl

import ru.otus.sc.game.dao.{GameProcessDao, GameStoreDao}
import ru.otus.sc.game.model.{Player, Position}

import scala.collection.mutable
import scala.util.Random

class GameProcessDaoImpl(store: GameStoreDao) extends GameProcessDao {
  private val map: mutable.Map[Position, Player] = mutable.Map[Position, Player]()
  private val size: Int                          = 100
  private val rand                               = new Random(size)

  @scala.annotation.tailrec
  private def freeCell(): Position = {
    val pos = Position(rand.nextInt(size), rand.nextInt(size))
    if (!map.contains(pos))
      pos
    else
      freeCell()
  }

  /**
    * Игрок заходит на карту
    *
    * @param player игрок
    */
  override def newGame(player: Player): Position = {
    val pos = freeCell()
    this.map += (pos -> player)
    this.store.savePlayer(player, pos)
    pos
  }

  /**
    * Загружаем игрока на карте
    *
    * @param player игрок
    * @return восстановленный игрок
    */
  override def loadPlayer(player: Player): Player = {
    val saved = store.loadPlayer(player)

    if (saved.isDefined) {
      if (this.map.contains(saved.get.position)) {
        this.map += (freeCell() -> saved.get.player)
      } else {
        this.map += (saved.get.position -> saved.get.player)
      }
      saved.get.player
    } else {
      val pos = freeCell()
      this.map += (pos -> player)
      this.store.savePlayer(player, pos)
      player
    }
  }

  /**
    * Игрок заходит в игру.
    * Надо сделать поиск сохранения и предложить пользователю выбор
    *
    * @param player игрок
    * @return
    */
  override def enterGame(player: Player): Position = {
    Position(0, 0)
  }
}
