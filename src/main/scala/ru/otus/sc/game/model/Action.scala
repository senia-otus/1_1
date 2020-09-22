package ru.otus.sc.game.model

/**
  * Игровое дейтсвие
  */
trait Action {

  /**
    * Название
    *
    * @return название
    */
  def name(): String

  /**
    * Характеристика, на которую воздействует
    *
    * @return имя характеристики
    */
  def changedField(): String

  /**
    * Значение, на которое меняется характеристика
    *
    * @return значение изменения
    */
  def changedValue(): Double

  /**
    * Просто красивый вывод
    *
    * @return
    */
  override def toString: String =
    s"${this.name()} [${this.changedField()} = ${this.changedValue()}]"
}

/**
  * Вообще ничего не делает
  */
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
