package ru.otus.sc.user.service.impl

import ru.otus.sc.comment.data.dao.CommentDao
import ru.otus.sc.common.ImplicitHelpers
import ru.otus.sc.post.dao.PostDao
import ru.otus.sc.user.data.dao.UserDao
import ru.otus.sc.user.model.ProfileResponse
import ru.otus.sc.user.service.UserService

import scala.concurrent.Future

class UserServiceImpl(
    userDao: UserDao,
    postDao: PostDao,
    commentsDao: CommentDao
) extends UserService
    with ImplicitHelpers {

  override def aggregateProfileInfo(id: Long): Future[ProfileResponse] =
    for {
      posts    <- postDao.findByUserId(id)
      user     <- userDao.findUser(id)
      comments <- commentsDao.findAllByAuthor(id)
    } yield ProfileResponse(
      id = user.id,
      name = user.name,
      login = user.login,
      email = user.email,
      avatar = user.avatar,
      posts = posts.toList,
      comments = comments.toList
    )

}
