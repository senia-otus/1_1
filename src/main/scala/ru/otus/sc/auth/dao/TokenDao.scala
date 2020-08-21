package ru.otus.sc.auth.dao

trait TokenDao {

  def generateToken(str: String): String

}
