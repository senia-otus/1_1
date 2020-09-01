package ru.otus.sc.auth.data

import java.time.OffsetDateTime

import ru.otus.sc.DatabaseProvider
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

import scala.concurrent.Future

case class Token(
    value: String,
    createdAt: OffsetDateTime = OffsetDateTime.now(),
    updatedAt: OffsetDateTime = OffsetDateTime.now(),
    expiredAt: OffsetDateTime = OffsetDateTime.now().plusDays(1)
)

final case class TokensTable(tag: Tag) extends Table[Token](tag, Some("public"), "tokens") {

  def value(): Rep[String] = column[String]("value", O.PrimaryKey)

  def createdAt(): Rep[OffsetDateTime] = column[OffsetDateTime]("created_at")

  def updatedAt(): Rep[OffsetDateTime] = column[OffsetDateTime]("updated_at")

  def expiredAt(): Rep[OffsetDateTime] = column[OffsetDateTime]("expired_at")

  def *(): ProvenShape[Token] =
    (value(), createdAt(), updatedAt(), expiredAt()) <>
      (applyTokens.tupled, unapplyTokens)

  def applyTokens: (String, OffsetDateTime, OffsetDateTime, OffsetDateTime) => Token = {
    case (value, createdAt, updatedAt, expiredAt) =>
      Token(value, createdAt, updatedAt, expiredAt)
  }

  def unapplyTokens: Token => Option[(String, OffsetDateTime, OffsetDateTime, OffsetDateTime)] = {
    p =>
      Token.unapply(p).map {
        case (value, createdAt, updatedAt, expiredAt) => (value, createdAt, updatedAt, expiredAt)
      }
  }

}

object TokenRepository {

  val table = TableQuery[TokensTable]

  private val db = DatabaseProvider.db

  def upsert(token: Token): Future[Int] = db.run(table.insertOrUpdate(token))
  def findByToken(token: String) = db.run(table.filter { _.value() === token }.result.head)
}
