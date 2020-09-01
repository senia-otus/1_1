package ru.otus.sc.user.dao

import ru.otus.sc.user.model.User
import ru.otus.sc.user.model.User.UniqueUserId
import ru.otus.sc.user.model.UserTag.UserTagId

trait UserDao {
  def createUser(user: User): User
  def getUser(uniqueUserId: UniqueUserId): Option[User]
  def updateUser(user: User): Option[User]
  def deleteUser(uniqueUserId: UniqueUserId): Option[User]
  def findByFirstName(firstName: String): Seq[User]
  def findByLastName(lastName: String): Seq[User]
  def findByTag(tag: UserTagId): Seq[User]
  def getAllUsers: Seq[User]
}
