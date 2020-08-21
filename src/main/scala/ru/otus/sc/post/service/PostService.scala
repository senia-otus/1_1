package ru.otus.sc.post.service

import ru.otus.sc.greet.model.{GreetRequest, GreetResponse}
import ru.otus.sc.post.model.{PostDetailsView, PostView}

trait PostService {
  def findAllPosts(userId: Long): Seq[PostView]
  def findPost(id: Long): Option[PostDetailsView]
  def searchPosts(mask: String): Seq[PostView]
}
