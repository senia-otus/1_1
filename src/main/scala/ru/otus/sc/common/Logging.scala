package ru.otus.sc.common

import com.typesafe.scalalogging.Logger

trait Logging {

  implicit val logger = Logger(this.getClass.getName)

}
