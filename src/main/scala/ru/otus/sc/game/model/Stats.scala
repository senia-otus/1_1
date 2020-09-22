package ru.otus.sc.game.model

import scala.util.Random

/**
  * Маркер статов
  */
trait Stats {}

/**
  * Статы игрока по умолчанию
  */
object PlayerStats extends Stats {
  private val rand = new Random()

  /**
    * Базовая атака игрока
    *
    * @return
    */
  def playerAttack: Int = 20

  /**
    * Максимальное здоровье
    *
    * @return
    */
  def maxHealth: Double = 100.0

  /**
    * Сгенерировать случайную атаку
    *
    * @return
    */
  def randAttack(): Double = this.rand.nextDouble() * this.playerAttack

  /**
    * Случайный уровень здоровья
    *
    * @return
    */
  def randHealth(): Double = this.rand.nextDouble() * maxHealth
}

/**
  * Статы мира
  */
object GameStats extends Stats {

  /**
    * Размер игровой карты
    *
    * @return
    */
  def worldSize: Int = 10
}
