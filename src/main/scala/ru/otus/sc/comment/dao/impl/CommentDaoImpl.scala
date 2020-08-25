package ru.otus.sc.comment.dao.impl

import ru.otus.sc.comment.dao.CommentDao
import ru.otus.sc.comment.model.Comment

import scala.concurrent.Future

class CommentDaoImpl extends CommentDao {
  override def find(postId: Long): Future[Seq[Comment]] = ???

  override def getComment(id: Long): Future[Comment] = ???

  override def createComment(comment: Comment): Future[Comment] = ???

  override def deleteComment(id: Long): Unit = ???
}
