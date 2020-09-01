package ru.otus.sc

import ru.otus.sc.Engine.{Greeted, StorageKey, StorageValue}
import ru.otus.sc.countdown.model._
import ru.otus.sc.counter.model._
import ru.otus.sc.echo.model.{EchoRequest, EchoResponse}
import ru.otus.sc.greet.model.{GreetRequest, GreetResponse}
import ru.otus.sc.reverse.model.{ReverseRequest, ReverseResponse}
import ru.otus.sc.storage.model._
import ru.otus.sc.sum.model.{SumRequest, SumResponse}
import ru.otus.sc.user.model._

trait App {
  // reply on requested value with the same value
  // echo value can be multiplied up to 5 times with `repeatNum` value
  // no multiply answer by default (`repeatNum` is 1)
  // proper values return answer with `EchoAnswerResponse`
  // bad values return error with `EchoErrorResponse`
  def echo(request: EchoRequest): EchoResponse
  // greet with provided object
  // panic if `isHuman` flag is unset
  // greeting request requires implicit parameter for extracting name from the object
  // in current implementation object is User and implicit can be borrowed from
  // `ru.otus.sc.user.implicits.UserNameImplicits._`
  def greet(request: GreetRequest[Greeted]): GreetResponse
  // reverse given string from end to begin
  def reverse(request: ReverseRequest): ReverseResponse
  // sum 2 given values locally
  // sum can be performed externally with `external` flag
  // in current implementation external computation is artificial delay
  // which was implemented with lazy value
  def sum(request: SumRequest): SumResponse
  // manage users
  // user is compound from:
  //   id, which is DB related stuff like pk
  //   unique id, UUID, this item is unique for whole system
  //   user name, which is compound from: first, last, middle, patronymic names and title
  //   age
  //   set of tags
  // CRUD operations are available as well as search for first, last name and tag
  def createUser(request: CreateUserRequest): CreateUserResponse
  def getUser(request: GetUserRequest): GetUserResponse
  def deleteUser(request: DeleteUserRequest): DeleteUserResponse
  def updateUser(request: UpdateUserRequest): UpdateUserResponse
  def findUsers(request: FindUsersRequest): FindUsersResponse
  // manage user tags
  // tags are bound with users and related only them
  // tag is compound from:
  //   id, which is DB related stuff like pk
  //   name, for human
  // CRUD operations are available as well as search for name
  // also associated with certain tag users can be obtained in search request
  // tags and users are totally independent
  // tagging and untagging users do not allow manage tags on users directly
  // and have to be performed as separate operations for users and tags
  // e.g. tagging user example,
  // imagine we already have app, user, tag and all operations are done successfully:
  //  val tagId = 42L
  //  val uniqueUserId = UUID.fromString("4a71a58b-4b39-44fc-ae52-76b9657be280")
  //  val tag = app.getUserTag(GetUserTagRequest(tagId))
  //  val user = app.getUser(GetUserRequest(uniqueUserId))
  //  val updatedUser = user.copy(tags = (user.tags + tag))
  //  app.tagUser(UpdateTagUserRequest(tagId, uniqueUserId))
  //  app.updateUser(UpdateUserRequest(updatedUser))
  def createUserTag(request: CreateUserTagRequest): CreateUserTagResponse
  def getUserTag(request: GetUserTagRequest): GetUserTagResponse
  def deleteUserTag(request: DeleteUserTagRequest): DeleteUserTagResponse
  def updateUserTag(request: UpdateUserTagRequest): UpdateUserTagResponse
  def findUserTags(request: FindUserTagsRequest): FindUserTagsResponse
  def tagUser(request: UpdateTagUserRequest): UpdateTagUserResponse
  def untagUser(request: UpdateUntagUserRequest): UpdateUntagUserResponse
  // manage counters
  // counter is auto-increment item with initial `value`, default value is 1
  // update increments counter by 1
  // counter value can not be updated itself, it can be only incremented
  // timestamp is updated on every counter update
  // counter is compound from:
  //   id, UUID, this item is unique for whole system
  //   timestamp of update
  //   value of counter
  // CRUD operations are available as well as search for values or timestamps
  // searching requires predicate (t: T, i: T) => Boolean for comparing items
  // where `t` is target and `i` is item in DB
  def createCounter(request: CreateCounterRequest): CreateCounterResponse
  def deleteCounter(request: DeleteCounterRequest): DeleteCounterResponse
  def updateCounter(request: UpdateCounterRequest): UpdateCounterResponse
  def getCounter(request: GetCounterRequest): GetCounterResponse
  def findCounters(request: FindCountersRequest): FindCountersResponse
  // manage countdowns
  // countdown is auto-decrement item with initial `value`, default value is 1
  // update auto-decreases value by 1
  // when countdown reaches 0 it stops decrease value and swaps state from Tick to Done
  // Countdown.Done is composed from id, UUID, this item is unique for whole system
  // State above can not be updated anymore, also it can be created `CountdownDone`
  // Countdown.Tick is composed from:
  //   id, UUID, this item is unique for whole system
  //   updater, UUID this is id of something that create or update countdown
  //   value of countdown
  // CRUD operations are available as well as search for:
  //   values, updaters, Done and NonDone states
  // searching for values requires predicate (t: T, i: T) => Boolean for comparing items
  // where `t` is target and `i` is item in DB
  def createCountdown(request: CreateCountdownRequest): CreateCountdownResponse
  def deleteCountdown(request: DeleteCountdownRequest): DeleteCountdownResponse
  def updateCountdown(request: UpdateCountdownRequest): UpdateCountdownResponse
  def getCountdown(request: GetCountdownRequest): GetCountdownResponse
  def findCountdowns(request: FindCountdownsRequest): FindCountdownsResponse
  // manage storage
  // provide value by requested key
  // StorageEntry is composed from key and value of defined types
  // in current implementation keys and values are String
  // CRUD operations are available as well as search for value itself or predicate on value
  // searching with predicate requires predicate i: V => Boolean for comparing items
  // where `i` is item in DB
  def createStorage(
      request: CreateStorageRequest[StorageKey, StorageValue]
  ): CreateStorageResponse[StorageKey, StorageValue]
  def getStorage(
      request: GetStorageRequest[StorageKey, StorageValue]
  ): GetStorageResponse[StorageKey, StorageValue]
  def deleteStorage(
      request: DeleteStorageRequest[StorageKey, StorageValue]
  ): DeleteStorageResponse[StorageKey, StorageValue]
  def updateStorage(
      request: UpdateStorageRequest[StorageKey, StorageValue]
  ): UpdateStorageResponse[StorageKey, StorageValue]
  def findStorages(
      request: FindStoragesRequest[StorageKey, StorageValue]
  ): FindStoragesResponse[StorageKey, StorageValue]
}

object App {
  def apply(): App = new AppImpl(Engine())

  private class AppImpl(engine: Engine) extends App {
    def echo(request: EchoRequest): EchoResponse =
      engine.echoing.echo(request)
    def greet(request: GreetRequest[Greeted]): GreetResponse =
      engine.greeting.greet(request)
    def reverse(request: ReverseRequest): ReverseResponse =
      engine.reversing.reverse(request)
    def sum(request: SumRequest): SumResponse =
      engine.summing.sum(request)
    def createUser(request: CreateUserRequest): CreateUserResponse =
      engine.usering.createUser(request)
    def getUser(request: GetUserRequest): GetUserResponse =
      engine.usering.getUser(request)
    def deleteUser(request: DeleteUserRequest): DeleteUserResponse =
      engine.usering.deleteUser(request)
    def updateUser(request: UpdateUserRequest): UpdateUserResponse =
      engine.usering.updateUser(request)
    def findUsers(request: FindUsersRequest): FindUsersResponse =
      engine.usering.findUsers(request)
    def createUserTag(request: CreateUserTagRequest): CreateUserTagResponse =
      engine.userTagging.createUserTag(request)
    def getUserTag(request: GetUserTagRequest): GetUserTagResponse =
      engine.userTagging.getUserTag(request)
    def deleteUserTag(request: DeleteUserTagRequest): DeleteUserTagResponse =
      engine.userTagging.deleteUserTag(request)
    def updateUserTag(request: UpdateUserTagRequest): UpdateUserTagResponse =
      engine.userTagging.updateUserTag(request)
    def findUserTags(request: FindUserTagsRequest): FindUserTagsResponse =
      engine.userTagging.findUserTags(request)
    def tagUser(request: UpdateTagUserRequest): UpdateTagUserResponse =
      engine.userTagging.tagUser(request)
    def untagUser(request: UpdateUntagUserRequest): UpdateUntagUserResponse =
      engine.userTagging.untagUser(request)
    def createCounter(request: CreateCounterRequest): CreateCounterResponse =
      engine.counting.createCounter(request)
    def deleteCounter(request: DeleteCounterRequest): DeleteCounterResponse =
      engine.counting.deleteCounter(request)
    def updateCounter(request: UpdateCounterRequest): UpdateCounterResponse =
      engine.counting.updateCounter(request)
    def getCounter(request: GetCounterRequest): GetCounterResponse =
      engine.counting.getCounter(request)
    def findCounters(request: FindCountersRequest): FindCountersResponse =
      engine.counting.findCounters(request)
    def createCountdown(request: CreateCountdownRequest): CreateCountdownResponse =
      engine.countdowning.createCountdown(request)
    def deleteCountdown(request: DeleteCountdownRequest): DeleteCountdownResponse =
      engine.countdowning.deleteCountdown(request)
    def updateCountdown(request: UpdateCountdownRequest): UpdateCountdownResponse =
      engine.countdowning.updateCountdown(request)
    def getCountdown(request: GetCountdownRequest): GetCountdownResponse =
      engine.countdowning.getCountdown(request)
    def findCountdowns(request: FindCountdownsRequest): FindCountdownsResponse =
      engine.countdowning.findCountdowns(request)
    def createStorage(
        request: CreateStorageRequest[StorageKey, StorageValue]
    ): CreateStorageResponse[StorageKey, StorageValue] =
      engine.getting.createStorage(request)
    def deleteStorage(
        request: DeleteStorageRequest[StorageKey, StorageValue]
    ): DeleteStorageResponse[StorageKey, StorageValue] =
      engine.getting.deleteStorage(request)
    def updateStorage(
        request: UpdateStorageRequest[StorageKey, StorageValue]
    ): UpdateStorageResponse[StorageKey, StorageValue] =
      engine.getting.updateStorage(request)
    def getStorage(
        request: GetStorageRequest[StorageKey, StorageValue]
    ): GetStorageResponse[StorageKey, StorageValue] =
      engine.getting.getStorage(request)
    def findStorages(
        request: FindStoragesRequest[StorageKey, StorageValue]
    ): FindStoragesResponse[StorageKey, StorageValue] =
      engine.getting.findStorages(request)
  }
}
