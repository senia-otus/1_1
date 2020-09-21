package ru.otus.sc.game.model

import ru.otus.sc.game.model.GameProcessState.GameProcessState

import scala.util.Random

/**
  * Пользователь, под которым игрок заходит в игру
  *
  * @param nick     никнейм, который отображается в игре
  * @param username имя пользователь
  */
case class User(nick: String, username: String)

/**
  * Игрок
  *
  * @param nick   никнейм
  * @param health здоровье
  */
case class Player(
    nick: String,
    health: Double,
    attack: Double = PlayerStats.playerAttack,
    isEnemy: Boolean = false
) extends Entity {
  override def symbol: String = if (!this.isEnemy) "*" else "E"

  override def action: Action = NoAction

  override def toString: String = s"$nick [H-{$health}, D-{$attack}]"
}

object Player {
  private val rand = new Random()

  private def generateName(): String = {
    List
      .range(0, 10)
      .foldLeft("")((acc, _) => {
        acc + rand.nextPrintableChar()
      })
  }

  def enemy(): Player = {
    Player(
      generateName(),
      PlayerStats.maxHealth,
      this.rand.nextInt(PlayerStats.playerAttack / 2) + (PlayerStats.playerAttack / 4),
      isEnemy = true
    )
  }
}

object PlayerStats {
  private val rand = new Random()

  def playerAttack: Int = 20

  def maxHealth: Double = 100.0

  def randAttack(): Double = this.rand.nextDouble() * 20

  def randHealth(): Double = this.rand.nextDouble() * maxHealth
}

object GameStats {
  def worldSize: Int = 20
}

/**
  * Информация о пользователе для сохранения/восстановления
  *
  * @param player   игрок
  * @param position позиция
  */
case class PlayerStore(player: Player, position: Position)

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
    map: Map[Position, Entity],
    pos: Position
) {
  override def toString: String =
    s"""player [${player.toString}]
       |state  [${state.toString}]
       |map    [${GameMap(map + (pos -> player)).present(GameStats.worldSize)}]
       |pos    [${pos.toString}]
       |""".stripMargin
}

/**
  * Игровое состояние игрока
  *
  * None - игрока пока нет нигде
  * MainMenu - игрок находиться в главном меню
  * GameMap - игрок находится на игровой карте
  * Lose - игрок проиграл
  * Win - игрок выиграл
  */
object GameProcessState extends Enumeration {
  type GameProcessState = Value
  val NONE, MAIN_MENU, GAME, LOSE, WIN = Value
}
