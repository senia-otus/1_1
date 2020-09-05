package ru.otus.sc.post.model

import java.time.OffsetDateTime

import ru.otus.sc.comment.model.CommentView
import ru.otus.sc.tags.model.Tag
import ru.otus.sc.user.model.UserView

case class PostDetailsView(post: Post, author: UserView, tags: Seq[Tag], comments: Seq[CommentView])
case class PostView(
    id: Long,
    title: String,
    description: Option[String],
    image: Option[String],
    date: OffsetDateTime,
    user: Option[UserView]
)
