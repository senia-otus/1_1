package ru.otus.sc.game.model

import scala.util.Random

object GameMap {
  val size: Int      = 10
  val entsCount: Int = size / 5

  def apply(level: Int = 0): GameMap = {
    require(level <= 5, "Уровень не должен быть больше 5")
    val rand = new Random(10)

    @scala.annotation.tailrec
    def freeCell(currentMap: Map[Position, GameEntity]): Position = {
      val pos = Position(rand.nextInt(size), rand.nextInt(size))
      if (!currentMap.contains(pos))
        pos
      else
        freeCell(currentMap)
    }

    GameMap(
      List
        .range(0, (level + 1) * entsCount)
        .foldLeft(Map[Position, GameEntity]())((acc, _) => {
          if (rand.nextInt(size) < size / 3)
            acc + (freeCell(acc) -> Chest())
          else
            acc + (freeCell(acc) -> Trap())
        })
    )
  }
}

/**
  * Хранение игровой карты
  * Но сильно переоценено, возможно потом понадобится
  *
  * @param entities элементы карты
  */
case class GameMap(entities: Map[Position, GameEntity]) {
  def present(size: Int = GameStats.worldSize): String = {
    val buff = new StringBuilder("")

    for {
      j <- 0 until size
      i <- 0 until size
    } {
      val pos = Position(i, j)
      buff ++= " "
      if (entities.contains(pos))
        buff ++= entities(pos).symbol
      else
        buff ++= "_"
      buff ++= " "

      if (i == size - 1)
        buff ++= "\n"
    }
    buff.toString()
  }

  def show(): Unit = {
    println(this.present())
  }

  def place(add: Map[Position, GameEntity] => Map[Position, GameEntity]): GameMap = {
    GameMap(add(this.entities))
  }
}
