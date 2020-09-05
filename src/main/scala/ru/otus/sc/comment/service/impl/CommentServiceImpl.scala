package ru.otus.sc.comment.service.impl

import ru.otus.sc.comment.data.dao.CommentDao
import ru.otus.sc.comment.model.CommentView
import ru.otus.sc.comment.service.CommentService

import scala.concurrent.Future

class CommentServiceImpl(dao: CommentDao) extends CommentService {
  override def find(postId: Long): Future[Seq[CommentView]] = ???

  override def addComment(text: String, authorId: Long): Future[CommentView] = ???

  override def search(name: String): Future[Seq[CommentView]] = ???
}
