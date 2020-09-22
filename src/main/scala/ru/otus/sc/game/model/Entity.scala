package ru.otus.sc.game.model

trait GameEntity {
  def symbol: String = " "

  def action: Action
}

case class Chest() extends GameEntity {
  override def symbol: String = "C"

  override def action: Action = ActionRestoreHealth(PlayerStats.randHealth() / 8)
}

case class Trap() extends GameEntity {
  override def symbol: String = "T"

  override def action: Action = ActionDamageFromTrap(PlayerStats.randAttack() / 2)
}
