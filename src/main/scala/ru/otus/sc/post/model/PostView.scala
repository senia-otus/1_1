package ru.otus.sc.post.model

import java.time.OffsetDateTime

import ru.otus.sc.comment.model.Comment
import ru.otus.sc.tags.model.Tag
import ru.otus.sc.user.model.User

case class PostDetailsView(post: Post, author: User, tags: Seq[Tag], comments: Seq[Comment])
case class PostView(id: Long, title: String, description: Option[String], image: Option[String], date: OffsetDateTime, user: Option[User])
