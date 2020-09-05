package ru.otus.sc.user.data

import java.sql.Timestamp
import java.time.Instant

import ru.otus.sc.DatabaseProvider
import ru.otus.sc.auth.data.TokenRepository
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape
import slick.sql.SqlProfile.ColumnOption.SqlType

import scala.concurrent.Future

case class User(
    id: Long = 0,
    name: String,
    login: String,
    email: String,
    password: String,
    tokenId: Option[String],
    avatar: Option[String],
    createdAt: Timestamp = Timestamp.from(Instant.now()),
    updatedAt: Timestamp = Timestamp.from(Instant.now()),
    deletedAt: Option[Timestamp] = None
)

final case class UsersTable(tag: Tag) extends Table[User](tag, Some("public"), "users") {

  def id(): Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def name(): Rep[String] = column[String]("name")

  def login(): Rep[String] = column[String]("login")

  def email(): Rep[String] = column[String]("email")

  def password(): Rep[String] = column[String]("password")

  def tokenId(): Rep[String] = column[String]("token")

  def avatar(): Rep[String] = column[String]("avatar")

  def updatedAt(): Rep[Timestamp] =
    column[Timestamp](
      "updated_at",
      SqlType("timestamp not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    )

  def createdAt(): Rep[Timestamp] =
    column[Timestamp](
      "created_at",
      SqlType("timestamp not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    )

  def deletedAt(): Rep[Timestamp] =
    column[Timestamp]("deleted_at", SqlType("timestamp default null"))

  def *(): ProvenShape[User] =
    (
      id(),
      name(),
      login(),
      email(),
      password(),
      tokenId().?,
      avatar().?,
      createdAt(),
      updatedAt(),
      deletedAt().?
    ) <> (applyUsers.tupled, unapplyUsers)

  def applyUsers: (
      Long,
      String,
      String,
      String,
      String,
      Option[String],
      Option[String],
      Timestamp,
      Timestamp,
      Option[Timestamp]
  ) => User = {
    case (id, name, login, email, password, tokenId, avatar, createdAt, updatedAt, deletedAt) =>
      User(id, name, login, email, password, tokenId, avatar, createdAt, updatedAt, deletedAt)
  }

  def unapplyUsers: User => Option[
    (
        Long,
        String,
        String,
        String,
        String,
        Option[String],
        Option[String],
        Timestamp,
        Timestamp,
        Option[Timestamp]
    )
  ] = { u =>
    User.unapply(u).map {
      case (id, name, login, email, password, tokenId, avatar, createdAt, updatedAt, deletedAt) =>
        (id, name, login, email, password, tokenId, avatar, updatedAt, createdAt, deletedAt)
    }
  }

  def token() = foreignKey("token_users", tokenId(), TokenRepository.tokens)(_.value())

}

object UserRepository {

  val users = TableQuery[UsersTable]

  private val db = DatabaseProvider.db

  def findById(id: Long): Future[User] = db.run(users.filter { _.id() === id }.result.head)

  def findAllByLogin(login: String): Future[Seq[User]] =
    db.run(users.filter { _.login() === login }.result)

  def save(user: User): Future[User] = {
    val insertQuery = users returning users.map(_.id()) into ((table, id) => table.copy(id = id))
    val action      = insertQuery += user
    db.run(action)
  }
}
