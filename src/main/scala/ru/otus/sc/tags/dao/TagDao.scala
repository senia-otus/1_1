package ru.otus.sc.tags.dao

import ru.otus.sc.tags.model.Tag

import scala.concurrent.Future

trait TagDao {
  def all(): Future[Seq[Tag]]
  def find(postId: Long): Future[Seq[Tag]]
  def search(name: String): Future[Seq[Tag]]
}
