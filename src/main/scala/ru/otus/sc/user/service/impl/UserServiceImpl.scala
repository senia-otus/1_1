package ru.otus.sc.user.service.impl

import ru.otus.sc.greet.dao.GreetingDao
import ru.otus.sc.greet.model.{GreetRequest, GreetResponse}
import ru.otus.sc.greet.service.GreetingService
import ru.otus.sc.user.model.ProfileResponse
import ru.otus.sc.user.service.UserService

class UserServiceImpl(dao: GreetingDao) extends UserService {

  override def aggregateProfileInfo(id: Long): ProfileResponse = ???

}
