package ru.otus.sc.reverse.dao.impl

import ru.otus.sc.reverse.dao.ReverseDao

import scala.annotation.tailrec

class ReverseDaoImpl extends ReverseDao {
  def reverse(word: String): String = {
    if (word.length < 2) word
    else reverseWord(word)()(word.length - 1)
  }

  // Can not use .reverse, so use recursion
  @tailrec
  private def reverseWord(source: String)(acc: String = "")(position: Int): String = {
    if (position < 0) acc
    else reverseWord(source)(s"$acc${source.charAt(position)}")(position - 1)
  }
}
