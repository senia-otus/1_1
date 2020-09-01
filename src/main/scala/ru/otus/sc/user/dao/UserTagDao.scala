package ru.otus.sc.user.dao

import ru.otus.sc.user.model.User.UniqueUserId
import ru.otus.sc.user.model.UserTag
import ru.otus.sc.user.model.UserTag.UserTagId

trait UserTagDao {
  def createTag(tag: UserTag): UserTag
  def getTag(tagId: UserTagId): Option[UserTag]
  def updateTag(tag: UserTag): Option[UserTag]
  def deleteTag(tagId: UserTagId): Option[UserTag]
  def findByName(tagName: String): Seq[UserTag]
  def getUsersByTag(tagId: UserTagId): Seq[UniqueUserId]
  def getAllTags: Seq[UserTag]
  def tagUser(tagId: UserTagId, uniqueUserId: UniqueUserId): Option[(UserTagId, UniqueUserId)]
  def untagUser(tagId: UserTagId, uniqueUserId: UniqueUserId): Option[(UserTagId, UniqueUserId)]
}
