package ru.otus.sc.post.dao.impl

import java.time.OffsetDateTime

import ru.otus.sc.comment.dao.CommentDao
import ru.otus.sc.common.DB
import ru.otus.sc.post.dao.PostDao
import ru.otus.sc.post.db.FakePostDb
import ru.otus.sc.post.model.{Post, PostDetailsView, PostView}
import ru.otus.sc.tags.dao.TagDao
import ru.otus.sc.tags.model.Tag
import ru.otus.sc.user.data.dao.UserDao
import ru.otus.sc.user.model.User

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Success, Try}

class PostDaoImpl extends PostDao {

  private implicit val context = ExecutionContext.global
  private val db: DB[Post, Long] = FakePostDb

  override def allPosts(): Future[Seq[Post]] = db.all()

  override def getPost(id: Long): Future[Post] = db.read(id)

  override def createPost(post: Post): Future[Post] = db.save(post)

  override def updatePost(id: Long, post: Post): Future[Post] = db.read(id)

  override def deletePost(id: Long): Unit = db.delete(id)
}
