package ru.otus.sc

import ru.otus.sc.auth.data.dao.impl.AuthDaoImpl
import ru.otus.sc.auth.model.{AuthRequest, AuthResponse, RegisterRequest, RegisterResponse}
import ru.otus.sc.auth.service.AuthService
import ru.otus.sc.auth.service.impl.AuthServiceImpl
import ru.otus.sc.comment.dao.impl.CommentDaoImpl
import ru.otus.sc.greet.dao.impl.GreetingDaoImpl
import ru.otus.sc.greet.model.{GreetRequest, GreetResponse}
import ru.otus.sc.greet.service.GreetingService
import ru.otus.sc.greet.service.impl.GreetingServiceImpl
import ru.otus.sc.post.dao.impl.PostDaoImpl
import ru.otus.sc.post.model.{PostDetailsView, PostView}
import ru.otus.sc.post.service.PostService
import ru.otus.sc.post.service.impl.PostServiceImpl
import ru.otus.sc.tags.dao.impl.TagDaoImpl
import ru.otus.sc.user.data.dao.impl.UserDaoImpl

import scala.concurrent.Future

trait Application {
  def greet(request: GreetRequest): GreetResponse
  def auth(request: AuthRequest): Future[AuthResponse]
  def register(request: RegisterRequest): RegisterResponse

  def findAllPosts(userId: Long): Seq[PostView]
  def findPost(id: Long): Option[PostDetailsView]
  def searchPosts(mask: String): Seq[PostView]
}

object Application {

  private class ApplicationImpl(
                         greeting: GreetingService,
                         authService: AuthService,
                         postService: PostService
                       ) extends Application {

    override def greet(request: GreetRequest): GreetResponse = greeting.greet(request)
    override def auth(request: AuthRequest): Future[AuthResponse] = authService.auth(request)
    override def register(request: RegisterRequest): RegisterResponse = authService.register(request)

    override def findAllPosts(userId: Long): Seq[PostView] = postService.findAllPosts(userId)
    override def findPost(id: Long): Option[PostDetailsView] = postService.findPost(id)
    override def searchPosts(mask: String): Seq[PostView] = postService.searchPosts(mask)

  }

  def apply(): Application = {
    val greetingDao     = new GreetingDaoImpl  
    val greetingService = new GreetingServiceImpl(greetingDao)
    val authDao         = new AuthDaoImpl
    val userDao         = new UserDaoImpl
    val postDao         = new PostDaoImpl
    val commentsDao     = new CommentDaoImpl
    val tagsDao         = new TagDaoImpl
    val authService     = new AuthServiceImpl(authDao)
    val postService     = new PostServiceImpl(postDao, userDao, commentsDao, tagsDao)

    new ApplicationImpl(greetingService, authService, postService)
  }
}
