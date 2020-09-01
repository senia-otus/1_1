package ru.otus.sc.user.data

import java.time.OffsetDateTime

import ru.otus.sc.DatabaseProvider
import ru.otus.sc.auth.data.{Token, TokenRepository}
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

import scala.concurrent.Future

case class User(
    id: Long,
    name: String,
    login: String,
    email: String,
    password: String,
    token: Option[Token],
    avatar: Option[String],
    createdAt: OffsetDateTime,
    updatedAt: OffsetDateTime,
    deletedAt: Option[OffsetDateTime]
)

final case class UsersTable(tag: Tag) extends Table[User](tag, Some("public"), "users") {

  def id(): Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def name(): Rep[String] = column[String]("name")

  def login(): Rep[String] = column[String]("login")

  def password(): Rep[String] = column[String]("password")

  def tokenId(): Rep[String] = column[String]("token")

  def token() = foreignKey("token", tokenId(), TokenRepository.tokens)(_.value())

  def avatar(): Rep[String] = column[String]("avatar")

  def updatedAt(): Rep[OffsetDateTime] = column[OffsetDateTime]("updated_at")

  def createdAt(): Rep[OffsetDateTime] = column[OffsetDateTime]("created_at")

  def deletedAt(): Rep[OffsetDateTime] = column[OffsetDateTime]("deleted_at")

  def *(): ProvenShape[User] = (id(), name(), login(), password(), token(), avatar(), createdAt(), updatedAt(), deletedAt()) <> (applyUsers.tupled, unapplyUsers)

  def applyUsers: (
      Long,
      String,
      String,
      String,
      String,
      Option[Token],
      Option[String],
      OffsetDateTime,
      OffsetDateTime,
      Option[OffsetDateTime]
  ) => User = {
    case (id, name, login, password, tokenId, token, avatar, updatedAt, createdAt, deletedAt) =>
      User(id, name, login, password, tokenId, token, avatar, updatedAt, createdAt, deletedAt)
  }

  def unapplyUsers: User => Option[
    (
        Long,
        String,
        String,
        String,
        String,
        Option[Token],
        Option[String],
        OffsetDateTime,
        OffsetDateTime,
        Option[OffsetDateTime]
    )
  ] = { u =>
    User.unapply(u).map {
      case (id, name, login, password, tokenId, token, avatar, updatedAt, createdAt, deletedAt) =>
        (id, name, login, password, tokenId, token, avatar, updatedAt, createdAt, deletedAt)
    }
  }

}

object UserRepository {

  val table = TableQuery[UsersTable]

  private val db = DatabaseProvider.db

  def findByLogin(login: String): Future[Seq[User]] = db.run(table.filter { _.login() === login }.result.head)

}
