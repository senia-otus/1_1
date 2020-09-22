package ru.otus.sc.game.model

/**
  * Направление движения
  *
  * @param x направление по X
  * @param y направление по Y
  */
case class Direction(x: Int, y: Int) {
  require(x >= -1 && x <= 1, "X должен быть в диапазоне [-1, 1]")
  require(y >= -1 && y <= 1, "Y должен быть в диапазоне [-1, 1]")

  override def toString: String = {
    s"${if (x < 0) "LEFT"
    else if (x > 0) "RIGHT"
    else ""}${if (x != 0 && y != 0) "_"
    else ""}${if (y < 0) "UP"
    else if (y > 0) "DOWN"
    else ""}"
  }
}

object Direction {

  /**
    * Возможность задать через буквенное представление, как при выдаче списка противников
    *
    * @param dX Возможное значения [LEFT|RIGHT|EMPTY]
    * @param dY Возможное значения [UP|DOWN|EMPTY]
    * @return
    */
  def apply(dX: String, dY: String): Direction = {
    Direction(
      if (dX == "LEFT") -1 else if (dX == "RIGHT") 1 else 0,
      if (dY == "UP") -1 else if (dY == "DOWN") 1 else 0
    )
  }

  /**
    * Возможность задать через нижнее подчеркивание и буквами
    *
    * Например:
    * - LEFT_UP
    * - RIGHT_DOWN
    *
    * @param direction направление вектора
    * @return направление вектора
    */
  def apply(direction: String): Direction = {
    direction.split("_").toList match {
      case sX :: sY :: Nil =>
        Direction(sX, sY)
      case _ :: _ =>
        Direction(0, 0)
    }
  }

  /**
    * Все направления
    *
    * @return список всех направлений
    */
  def all: List[Direction] =
    List(
      Direction(-1, -1),
      Direction(0, -1),
      Direction(1, -1),
      Direction(-1, 0),
      Direction(1, 0),
      Direction(-1, 1),
      Direction(0, 1),
      Direction(1, 1)
    )
}
