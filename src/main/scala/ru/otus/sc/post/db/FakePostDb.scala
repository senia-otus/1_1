package ru.otus.sc.post.db

import java.time.OffsetDateTime

import ru.otus.sc.common.DB
import ru.otus.sc.post.exceptions.PostNotFoundException
import ru.otus.sc.post.model.Post
import ru.otus.sc.user.data.db.FakeUsersDb

import scala.collection.mutable.ListBuffer
import scala.concurrent.Future
import scala.util.Random

object FakePostDb extends DB[Post, Long] {

  private val r = Random

  override val entities: ListBuffer[Post] = new ListBuffer[Post]

  {
    for {
      users <- FakeUsersDb.all()
    } yield entities.addAll(
      Seq(
        new Post(
          id = increment(),
          title = r.nextString(10),
          description = None,
          content = r.nextString(100000),
          image = None,
          date = OffsetDateTime.now(),
          authorId = users.last.id,
          tagIds = List.empty[Long],
          commentIds = List.empty[Long]
        ),
        new Post(
          id = increment(),
          title = r.nextString(10),
          description = None,
          content = r.nextString(100000),
          image = None,
          date = OffsetDateTime.now(),
          authorId = users.head.id,
          tagIds = List.empty[Long],
          commentIds = List.empty[Long]
        )
      )
    )
  }

  override def increment(): Long = entities.maxBy(_.id).id + 1

  override def all(): Future[Seq[Post]] = entities.toList.future

  override def read(id: Long): Future[Post] =
    entities.find(_.id == id) match {
      case Some(v) => v.future()
      case None    => Future.failed(new PostNotFoundException)
    }

  override def save(entity: Post): Future[Post] = {
    entities
      .find(_.id == entity.id) match {
      case Some(v) => entities.insert(entities.indexOf(entity), v)
      case None    => entities.addOne(entity)
    }

    entities
      .find(_.id == entity.id)
      .getOrElse(Post.empty())
      .future
  }

  override def delete(id: Long): Unit =
    entities.remove(
      entities.indexOf(
        entities.find(_.id == id).get
      )
    )

}
