package ru.otus.sc.`lazy`.dao.impl

import ru.otus.sc.`lazy`.dao.LazyDao
import ru.otus.sc.`lazy`.model.LazyValue

class LazyDaoImpl extends LazyDao {
  def singleton[A: LazyValue]: A = implicitly[LazyValue[A]].get
}
