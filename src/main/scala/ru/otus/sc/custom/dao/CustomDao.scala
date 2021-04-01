package ru.otus.sc.custom.dao

trait CustomDao {
  def showCountMethods(): String
  def showCounts(method: String): Int
  def existMethod(method: String): Boolean
  def incMethod(method: String): Boolean
  def lazyValue(): String
  def testLazyValue(): Boolean
}
