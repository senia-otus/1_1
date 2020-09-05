package ru.otus.sc.auth.data

import java.sql.Timestamp
import java.time.Instant

import ru.otus.sc.DatabaseProvider
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

import scala.concurrent.Future

case class Token(
    value: String,
    createdAt: Timestamp = Timestamp.from(Instant.now()),
    updatedAt: Timestamp = Timestamp.from(Instant.now()),
    expiredAt: Timestamp = Timestamp.from(Instant.now().plusSeconds(60 * 60 * 24))
)

final case class TokensTable(tag: Tag) extends Table[Token](tag, Some("public"), "tokens") {

  def value(): Rep[String] = column[String]("value", O.PrimaryKey)

  def createdAt(): Rep[Timestamp] = column[Timestamp]("created_at")

  def updatedAt(): Rep[Timestamp] = column[Timestamp]("updated_at")

  def expiredAt(): Rep[Timestamp] = column[Timestamp]("expired_at")

  def *(): ProvenShape[Token] =
    (value(), createdAt(), updatedAt(), expiredAt()) <>
      (applyTokens.tupled, unapplyTokens)

  def applyTokens: (String, Timestamp, Timestamp, Timestamp) => Token = {
    case (value, createdAt, updatedAt, expiredAt) =>
      Token(value, createdAt, updatedAt, expiredAt)
  }

  def unapplyTokens: Token => Option[(String, Timestamp, Timestamp, Timestamp)] = { p =>
    Token.unapply(p).map {
      case (value, createdAt, updatedAt, expiredAt) => (value, createdAt, updatedAt, expiredAt)
    }
  }

}

object TokenRepository {

  val tokens = TableQuery[TokensTable]

  private val db = DatabaseProvider.db

  def upsert(token: Token): Future[Int] = db.run(tokens.insertOrUpdate(token))

  def findByToken(token: String) = db.run(tokens.filter { _.value() === token }.result.head)
}
