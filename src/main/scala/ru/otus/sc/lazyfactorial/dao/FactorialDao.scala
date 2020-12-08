package ru.otus.sc.lazyfactorial.dao

/**
 * Calculation factorial
 */
trait FactorialDao {
  def fact(n: Int): BigInt
}
