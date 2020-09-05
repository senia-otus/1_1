package ru.otus.sc.user.data.db

import java.sql.Timestamp
import java.time.Instant

import ru.otus.sc.auth.model.TokenView
import ru.otus.sc.common.DB
import ru.otus.sc.user.model.UserView

import scala.collection.mutable.ListBuffer
import scala.concurrent.Future
import scala.util.Random

object FakeUsersDb extends DB[UserView, Long] {

  private val r = Random

  override val entities: ListBuffer[UserView] = new ListBuffer[UserView]()

  {
    entities.addAll(
      Seq(
        new UserView(
          id = r.nextLong,
          name = r.nextString(20),
          login = r.nextString(10),
          email = r.nextString(10),
          password = r.nextString(8),
          token = None,
          avatar = None
        ),
        new UserView(
          id = r.nextLong,
          name = r.nextString(20),
          login = r.nextString(10),
          email = r.nextString(10),
          password = r.nextString(8),
          token = Some(
            TokenView(r.nextString(128), Timestamp.from(Instant.now().plusSeconds(60 * 60 * 24)))
          ),
          avatar = None
        )
      )
    )
  }

  def all(): Future[List[UserView]] = entities.toList.future

  override def read(id: Long): Future[UserView] =
    entities
      .find(_.id == id)
      .getOrElse(UserView.empty())
      .future

  override def save(entity: UserView): Future[UserView] =
    entities
      .addOne(entity)
      .find(_.id == entity.id)
      .getOrElse(UserView.empty())
      .future

  override def delete(id: Long) =
    entities.remove(
      entities.indexOf(
        entities.find(_.id == id).get
      )
    )

  override def increment(): Long = {
    val l = r.nextLong()
    if (entities.count(_.id == l) >= 1) {
      increment()
    } else l
  }

}
