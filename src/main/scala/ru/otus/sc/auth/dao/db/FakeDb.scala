package ru.otus.sc.auth.dao.db

import ru.otus.sc.auth.model.User

import scala.collection.mutable.ListBuffer

class FakeDb {

  private val users = new ListBuffer[User]()

  def getAllUsers(): Seq[User] = users.toList

  def insertUser(user: User) = {
     users += user

    getAllUsers()
  }

}
