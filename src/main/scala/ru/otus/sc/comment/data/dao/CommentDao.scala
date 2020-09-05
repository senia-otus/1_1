package ru.otus.sc.comment.data.dao

import ru.otus.sc.comment.model.CommentView

import scala.concurrent.Future

trait CommentDao {
  def findAllByPost(postId: Long): Future[Seq[CommentView]]
  def findAllByAuthor(userId: Long): Future[Seq[CommentView]]
  def findById(postId: Long): Future[CommentView]
  def createComment(comment: CommentView): Future[CommentView]
  def deleteComment(id: Long)
}
