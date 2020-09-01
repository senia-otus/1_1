package ru.otus.sc.user.service

import ru.otus.sc.user.model._

trait UserService {
  def createUser(request: CreateUserRequest): CreateUserResponse
  def getUser(request: GetUserRequest): GetUserResponse
  def updateUser(request: UpdateUserRequest): UpdateUserResponse
  def deleteUser(request: DeleteUserRequest): DeleteUserResponse
  def findUsers(request: FindUsersRequest): FindUsersResponse
}
