package ru.otus.sc.user.service

import ru.otus.sc.user.model.ProfileResponse

trait UserService {

  def aggregateProfileInfo(id: Long): ProfileResponse

}
