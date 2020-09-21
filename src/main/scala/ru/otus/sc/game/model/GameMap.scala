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

  def place(add: Map[Position, Entity] => Map[Position, Entity]): GameMap = {
    GameMap(add(this.entities))
  }
}

trait Entity {
  def symbol: String = " "

  def action: Action
}
case class Chest() extends Entity {
  override def symbol: String = "C"

  override def action: Action = ActionRestoreHealth(PlayerStats.randHealth() / 8)
}
case class Trap() extends Entity {
  override def symbol: String = "T"

  override def action: Action = ActionDamageFromTrap(PlayerStats.randAttack() / 2)
}
