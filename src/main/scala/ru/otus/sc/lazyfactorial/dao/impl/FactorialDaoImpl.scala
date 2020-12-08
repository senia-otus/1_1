package ru.otus.sc.lazyfactorial.dao.impl

import ru.otus.sc.lazyfactorial.dao.FactorialDao

import scala.annotation.tailrec

/**
 * Implementation calculation of factorial
 * @see FactorialDao
 */
class FactorialDaoImpl extends FactorialDao {

  /**
   * Recursive factorial function
   * @param n value for calculation
   * @return result factorial value
   */
  override def fact(n: Int): BigInt ={
    @tailrec
    def factTail(n: Int, acc: BigInt = 1): BigInt = {
      n match {
        case 0 => acc
        case _ => factTail(n-1, n * acc)
      }
    }
    factTail(n, 1)
  }
}
