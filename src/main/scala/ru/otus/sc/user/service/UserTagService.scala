package ru.otus.sc.user.service

import ru.otus.sc.user.model._

trait UserTagService {
  def createUserTag(request: CreateUserTagRequest): CreateUserTagResponse
  def getUserTag(request: GetUserTagRequest): GetUserTagResponse
  def updateUserTag(request: UpdateUserTagRequest): UpdateUserTagResponse
  def deleteUserTag(request: DeleteUserTagRequest): DeleteUserTagResponse
  def findUserTags(request: FindUserTagsRequest): FindUserTagsResponse
  def tagUser(request: UpdateTagUserRequest): UpdateTagUserResponse
  def untagUser(request: UpdateUntagUserRequest): UpdateUntagUserResponse
}
