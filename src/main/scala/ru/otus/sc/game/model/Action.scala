package ru.otus.sc.game.model

/**
  * Игровое дейтсвие
  */
trait Action {
  def name(): String

  def changedField(): String

  def changedValue(): Double

  override def toString: String =
    s"${this.name()} [${this.changedField()} = ${this.changedValue()}]"
}

case object NoAction extends Action {
  override def name(): String = ""

  override def changedField(): String = ""

  override def changedValue(): Double = 0.0
}

/**
  * Восстановление здоровья
  *
  * @param size количество
  */
case class ActionRestoreHealth(size: Double) extends Action {
  override def name(): String = "Restore Health"

  override def changedField(): String = "Health"

  override def changedValue(): Double = size
}

/**
  * Получение урона от Ловушки
  *
  * @param size урон
  */
case class ActionDamageFromTrap(size: Double) extends Action {
  override def name(): String = "Get damage from the Trap"

  override def changedField(): String = "Health"

  override def changedValue(): Double = size
}
