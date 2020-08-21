package ru.otus.sc.comment.service

import ru.otus.sc.comment.model.Comment
import ru.otus.sc.greet.model.{GreetRequest, GreetResponse}

import scala.concurrent.Future

trait CommentService {
  def find(postId: Long): Future[Seq[Comment]]
  def addComment(text: String, authorId: Long): Future[Comment]
  def search(name: String): Future[Seq[Comment]]
}
