package ru.otus.sc.sum.dao

trait SumDao {
  def sum(a: Long, b: Long, external: Boolean): String
}
