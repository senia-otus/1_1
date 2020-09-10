package ru.otus.sc.game.model

/**
  * Пользователь, под которым игрок заходит в игру
  *
  * @param nick никнейм, который отображается в игре
  * @param username имя пользователь
  */
case class User(nick: String, username: String)

/**
  * Игрок
  *
  * @param nick никнейм
  * @param health здоровье
  */
case class Player(nick: String, health: Double) extends Entity {
  override def symbol: String = "*"
}
object PlayerStats {
  def maxHealth: Double = 100.0
}

/**
  * Информация о пользователе для сохранения/восстановления
  *
  * @param player игрок
  * @param position позиция
  */
case class PlayerStore(player: Player, position: Position)
