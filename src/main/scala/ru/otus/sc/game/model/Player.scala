package ru.otus.sc.game.model

import scala.util.Random

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
) extends GameEntity {
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
