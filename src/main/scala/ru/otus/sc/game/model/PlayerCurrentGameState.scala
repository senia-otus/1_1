package ru.otus.sc.game.model

import ru.otus.sc.game.model.GameProcessState.GameProcessState

/**
  * Текущее игровое состояние игрока
  *
  * @param player игрок
  * @param state  состояние игрока в игре
  * @param map    карта
  * @param pos    последняя позиция
  */
case class PlayerCurrentGameState(
    player: Player,
    state: GameProcessState = GameProcessState.NONE,
    map: Map[Position, GameEntity],
    pos: Position
) {
  override def toString: String =
    s"""player [\n${player.toString}]
       |pos    [\n${pos.toString}]
       |state  [\n${state.toString}]
       |map    [\n${GameMap(map + (pos -> player)).present(GameStats.worldSize)}]
       |""".stripMargin
}
