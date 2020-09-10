package ru.otus.sc.game.model

import scala.util.Random

object GameMap {
  val size: Int      = 10
  val entsCount: Int = size / 5

  def main(args: Array[String]): Unit = {
    val map = GameMap(5)
    map.show()
  }

  def apply(level: Int = 0): GameMap = {
    require(level <= 5, "Уровень не должен быть больше 5")
    val rand = new Random(10)

    @scala.annotation.tailrec
    def freeCell(currentMap: Map[Position, Entity]): Position = {
      val pos = Position(rand.nextInt(size), rand.nextInt(size))
      if (!currentMap.contains(pos))
        pos
      else
        freeCell(currentMap)
    }
    GameMap(
      List
        .range(0, (level + 1) * entsCount)
        .foldLeft(Map[Position, Entity]())((acc, _) => {
          if (rand.nextInt(size) < size / 3)
            acc + (freeCell(acc) -> Chest())
          else
            acc + (freeCell(acc) -> Trap())
        })
    )
  }
}

case class GameMap(entities: Map[Position, Entity]) {
  def show(): Unit = {
    for {
      i <- 0 until GameMap.size
      j <- 0 until GameMap.size
    } {
      val pos = Position(i, j)
      print(" ")
      if (entities.contains(pos))
        print(entities(pos).symbol)
      else
        print("_")
      print(" ")

      if (j == GameMap.size - 1)
        println()
    }
  }
}

trait Entity {
  def symbol: String = " "
}
case class Chest() extends Entity {
  override def symbol: String = "H"
}
case class Trap() extends Entity {
  override def symbol: String = "X"
}
