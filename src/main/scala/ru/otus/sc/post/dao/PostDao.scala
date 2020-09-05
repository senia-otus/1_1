package ru.otus.sc.post.dao

import ru.otus.sc.post.model.Post

import scala.concurrent.Future

trait PostDao {
  def findByUserId(userId: Long): Future[Seq[Post]]
  def getPost(id: Long): Future[Post]
  def createPost(post: Post, userId: Long): Future[Post]
  def updatePost(id: Long, post: Post): Future[Post]
  def deletePost(id: Long)

}
