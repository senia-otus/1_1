package ru.otus.sc.comment.service

import ru.otus.sc.comment.model.CommentView

import scala.concurrent.Future

trait CommentService {
  def find(postId: Long): Future[Seq[CommentView]]
  def addComment(text: String, authorId: Long): Future[CommentView]
  def search(name: String): Future[Seq[CommentView]]
}
