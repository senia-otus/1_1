package ru.otus.sc.post.model

import java.time.OffsetDateTime

import ru.otus.sc.comment.model.Comment
import ru.otus.sc.tags.model.Tag
import ru.otus.sc.user.model.User

class Post(
            val id: Long,
            val title: String,
            val description: Option[String],
            val content: String,
            val image: Option[String],
            val date: OffsetDateTime,
            val authorId: Long,
            val tagIds: List[Long],
            val commentIds: List[Long]
          )

object Post {
  def empty() = new Post(0, "", None, "", None, OffsetDateTime.MIN, 0, List.empty[Long], List.empty[Long])
}