package ru.otus.sc.comment.service.impl

import ru.otus.sc.comment.model.Comment
import ru.otus.sc.comment.service.CommentService
import ru.otus.sc.greet.dao.GreetingDao
import ru.otus.sc.greet.model.{GreetRequest, GreetResponse}
import ru.otus.sc.greet.service.GreetingService

import scala.concurrent.Future

class CommentServiceImpl(dao: GreetingDao) extends CommentService {
  override def find(postId: Long): Future[Seq[Comment]] = ???

  override def addComment(text: String, authorId: Long): Future[Comment] = ???

  override def search(name: String): Future[Seq[Comment]] = ???
}
