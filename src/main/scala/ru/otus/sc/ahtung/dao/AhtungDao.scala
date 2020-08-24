package ru.otus.sc.ahtung.dao

trait AhtungDao {
  def get(index: Int): Option[String]
}
