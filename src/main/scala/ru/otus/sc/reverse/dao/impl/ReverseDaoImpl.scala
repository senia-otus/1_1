package ru.otus.sc.reverse.dao.impl

import ru.otus.sc.reverse.dao.ReverseDao

class ReverseDaoImpl extends ReverseDao {
  // use string collection method
  def reverse(word: String): String = word.reverse
}
