package ru.otus.sc.user.model

import ru.otus.sc.comment.model.CommentView
import ru.otus.sc.post.model.Post

case class ProfileResponse(
    id: Long,
    name: String,
    login: String,
    email: String,
    avatar: Option[String],
    posts: List[Post],
    comments: List[CommentView]
)
