package ru.otus.sc.user.model

import ru.otus.sc.auth.model.TokenView
import ru.otus.sc.comment.model.Comment
import ru.otus.sc.post.model.Post
import ru.otus.sc.tags.model.Tag

case class ProfileResponse(
                            id: Long,
                            name: String,
                            login: String,
                            email: String,
                            token: Option[TokenView],
                            avatar: Option[String],
                            posts: List[Post],
                            comments: List[Comment],
                            tags: List[Tag]
                          )
