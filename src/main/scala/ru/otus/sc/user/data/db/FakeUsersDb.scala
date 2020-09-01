package ru.otus.sc.user.data.db

import java.time.OffsetDateTime

import ru.otus.sc.auth.model.TokenView
import ru.otus.sc.common.DB
import ru.otus.sc.user.model.User

import scala.collection.mutable.ListBuffer
import scala.concurrent.Future
import scala.util.Random

object FakeUsersDb extends DB[User, Long] {

  private val r = Random

  override val entities: ListBuffer[User] = new ListBuffer[User]()

  {
    entities.addAll(Seq(
      new User(
        id = r.nextLong,
        name = r.nextString(20),
        login = r.nextString(10),
        email = r.nextString(10),
        password = r.nextString(8),
        token = None,
        avatar = None
      ),
      new User(
        id = r.nextLong,
        name = r.nextString(20),
        login = r.nextString(10),
        email = r.nextString(10),
        password = r.nextString(8),
        token = Some(TokenView(r.nextString(128), OffsetDateTime.now().plusMonths(1))),
        avatar = None
      )
    ))
  }

  def all(): Future[List[User]] = entities.toList.future

  override def read(id: Long): Future[User] = entities
    .find(_.id == id)
    .getOrElse(User.empty())
    .future

  override def save(entity: User): Future[User] = entities
    .addOne(entity)
    .find(_.id == entity.id)
    .getOrElse(User.empty())
    .future

  override def delete(id: Long) = entities.remove(
    entities.indexOf(
      entities.find(_.id == id).get
    )
  )

  override def increment(): Long = {
    val l = r.nextLong()
    if(entities.count(_.id == l) >= 1) {
      increment()
    } else l
  }

  
}
