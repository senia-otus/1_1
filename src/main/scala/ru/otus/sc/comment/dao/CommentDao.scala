package ru.otus.sc.comment.dao

import ru.otus.sc.comment.model.Comment
import ru.otus.sc.post.model.Post

import scala.concurrent.Future

trait CommentDao {
  def find(postId: Long): Future[Seq[Comment]]
  def getComment(id: Long): Future[Comment]
  def createComment(comment: Comment): Future[Comment]
  def deleteComment(id: Long)
}
