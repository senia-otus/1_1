package ru.otus.sc.post.service.impl

import ru.otus.sc.comment.dao.CommentDao
import ru.otus.sc.common.ImplicitHelpers
import ru.otus.sc.post.dao.PostDao
import ru.otus.sc.post.model.{Post, PostDetailsView, PostView}
import ru.otus.sc.post.service.PostService
import ru.otus.sc.tags.dao.TagDao
import ru.otus.sc.tags.model.Tag
import ru.otus.sc.user.dao.UserDao

import scala.util.Success

class PostServiceImpl(
                       private val postDao: PostDao,
                       private val userDao: UserDao,
                       private val commentDao: CommentDao,
                       private val tagDao: TagDao
                     ) extends PostService with ImplicitHelpers {

  override def findAllPosts(userId: Long): Seq[PostView] = postDao.allPosts()
    .value.get match {
    case Success(value) => value.filter(_.authorId == userId).flatMap(mapToView)
    case _ => Seq.empty[PostView]
  }

  override def findPost(id: Long): Option[PostDetailsView] = postDao.getPost(id)
    .flatMap { post =>
      for {
        user <- userDao.findUser(post.authorId)
        comments <- commentDao.find(post.id)
        tags <- tagDao.find(post.id)
      } yield PostDetailsView(
        post,
        user,
        tags,
        comments
      )
    }
    .value
    .flatMap { tr =>
      tr match {
        case Success(value) => Some(value)
        case _ => None
      }
    }

  override def searchPosts(mask: String): Seq[PostView] = postDao.allPosts()
    .zip(tagDao.search(mask))
    .map { case (posts: Seq[Post], tags: Seq[Tag]) =>
      posts.filter(_.title == mask).map(p => mapToView(p)).map(_.get) ++ searchPostsByTagMask(posts, tags) ++ Nil
    }.value.get match {
      case Success(value) => value
      case _ => Seq.empty[PostView]
  }

  private def searchPostsByTagMask(posts: Seq[Post], tags: Seq[Tag]): Seq[PostView] = {
    posts
      .flatMap(_.tagIds)
      .filter(id => tags.map(_.id).contains(id))
      .flatMap { id => mapToView(posts.find(_.tagIds.contains(id)).get)}
  }

  private def mapToView(post: Post): Option[PostView] = {
    for {
      user <- userDao.findUser(post.authorId).value
    } yield PostView(post.id, post.title, post.description, post.image, post.date, user.toOption)
  }
}
