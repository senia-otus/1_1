package ru.otus.sc.user.service

import ru.otus.sc.user.model.ProfileResponse

import scala.concurrent.Future

trait UserService {

  def aggregateProfileInfo(id: Long): Future[ProfileResponse]

}
