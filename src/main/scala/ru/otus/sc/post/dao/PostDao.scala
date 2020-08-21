package ru.otus.sc.post.dao

import ru.otus.sc.post.model.{Post}

import scala.concurrent.Future

trait PostDao {
  def allPosts(): Future[Seq[Post]]
  def getPost(id: Long): Future[Post]
  def createPost(post: Post): Future[Post]
  def updatePost(id: Long, post: Post): Future[Post]
  def deletePost(id: Long)

}
