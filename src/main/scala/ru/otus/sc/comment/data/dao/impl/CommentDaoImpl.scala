package ru.otus.sc.comment.data.dao.impl

import ru.otus.sc.comment.data.dao.CommentDao
import ru.otus.sc.comment.model.CommentView

import scala.concurrent.Future

class CommentDaoImpl extends CommentDao {
  override def findAllByPost(postId: Long): Future[Seq[CommentView]] = ???

  override def findAllByAuthor(userId: Long): Future[Seq[CommentView]] = ???

  override def findById(postId: Long): Future[CommentView] = ???

  override def createComment(comment: CommentView): Future[CommentView] = ???

  override def deleteComment(id: Long): Unit = ???
}
