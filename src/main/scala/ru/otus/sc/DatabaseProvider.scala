package ru.otus.sc

import slick.jdbc.JdbcBackend.Database

object DatabaseProvider {
  val db = Database.forConfig("db")

  def close() = db.close()
}
