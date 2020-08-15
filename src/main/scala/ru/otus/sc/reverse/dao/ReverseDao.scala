package ru.otus.sc.reverse.dao

trait ReverseDao {
  def reverse(word: String): String
}
