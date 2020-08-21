package ru.otus.sc.common

import scala.collection.mutable.ListBuffer
import scala.concurrent.{ExecutionContext, Future}

trait DB[T, ID] extends ImplicitHelpers {


  val entities: ListBuffer[T]

  def increment(): ID
  def all(): Future[Seq[T]]
  def read(id: ID): Future[T]
  def save(entity: T): Future[T]
  def delete(id: ID)

}
