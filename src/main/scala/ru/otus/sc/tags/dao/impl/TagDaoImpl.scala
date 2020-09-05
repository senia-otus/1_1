package ru.otus.sc.tags.dao.impl

import ru.otus.sc.tags.dao.TagDao
import ru.otus.sc.tags.model.Tag

import scala.concurrent.Future

class TagDaoImpl extends TagDao {
  override def all(): Future[Seq[Tag]] = ???

  override def find(postId: Long): Future[Seq[Tag]] = ???

  override def search(name: String): Future[Seq[Tag]] = ???
}
