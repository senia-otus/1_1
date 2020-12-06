package ru.otus.sc.greet.model

import io.circe.Decoder.decodeLong
import io.circe.Encoder.encodeLong
import io.circe.{ Decoder, Encoder }

/**
 * case class идентификатор для дополнительной type safety
 */
case class Id[+A](value: Long) extends AnyVal
object Id {
  implicit def decoder[A]: Decoder[Id[A]] = decodeLong.map(Id.apply[A])
  implicit def encoder[A]: Encoder[Id[A]] = encodeLong.contramap(_.value)
}
