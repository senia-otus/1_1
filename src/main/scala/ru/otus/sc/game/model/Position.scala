package ru.otus.sc.game.model

/**
  * Позиция на карте
  *
  * @param x позиция по X
  * @param y позиция по Y
  */
case class Position(x: Int, y: Int) {
  def direct(x: Int, y: Int, maxX: Int, maxY: Int): Position = {
    val xN = this.x + x
    val yN = this.y + y
    Position(
      if (xN > maxX) maxX else if (xN < 0) 0 else xN,
      if (yN > maxY) maxY else if (yN < 0) 0 else yN
    )
  }

  def byDirection(direction: Direction, maxX: Int, maxY: Int): Position = {
    this.direct(direction.x, direction.y, maxX, maxY)
  }

  override def toString: String = s"[${x + 1}, ${y + 1}]"
}

object Position {
  def directionToString(pair: (Int, Int)): String = {
    this.directionToString(pair._1, pair._2)
  }

  def directionToString(x: Int, y: Int): String = {
    s"${if (x < 0) "LEFT"
    else if (x > 0) "RIGHT"
    else ""}${if (x != 0 && y != 0) "_"
    else ""}${if (y < 0) "UP"
    else if (y > 0) "DOWN"
    else ""}"
  }
}
