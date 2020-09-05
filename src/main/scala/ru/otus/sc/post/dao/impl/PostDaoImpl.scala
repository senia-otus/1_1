package ru.otus.sc.post.dao.impl

import ru.otus.sc.common.DB
import ru.otus.sc.post.dao.PostDao
import ru.otus.sc.post.db.FakePostDb
import ru.otus.sc.post.model.Post

import scala.concurrent.{ExecutionContext, Future}

class PostDaoImpl extends PostDao {

  private implicit val context   = ExecutionContext.global
  private val db: DB[Post, Long] = FakePostDb

  override def allPosts(): Future[Seq[Post]] = db.all()

  override def getPost(id: Long): Future[Post] = db.read(id)

  override def createPost(post: Post): Future[Post] = db.save(post)

  override def updatePost(id: Long, post: Post): Future[Post] = db.read(id)

  override def deletePost(id: Long): Unit = db.delete(id)
}
