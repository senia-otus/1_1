package ru.otus.sc.ahtung.dao.impl

import ru.otus.sc.ahtung.dao.AhtungDao

class AhtungDaoImpl extends AhtungDao {
  import AhtungDaoImpl.storage

  override def get(index: Int): Option[String] = storage.lift(index)
}

object AhtungDaoImpl {
  /** Simple, pre-filled storage */
  def storage = Seq("apple", "pen", "pineapple")
}

