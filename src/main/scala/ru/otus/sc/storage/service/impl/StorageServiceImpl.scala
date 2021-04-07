package ru.otus.sc.storage.service.impl

import ru.otus.sc.storage.dao.StorageDao
import ru.otus.sc.storage.model.{
  StorageDeleteBulkRequest,
  StorageDeleteBulkResponse,
  StorageDeleteRequest,
  StorageDeleteResponse,
  StorageFindBulkRequest,
  StorageFindBulkResponse,
  StorageFindRequest,
  StorageFindResponse,
  StorageKey,
  StorageUpdateBulkRequest,
  StorageUpdateBulkResponse,
  StorageUpdateRequest,
  StorageUpdateResponse,
  StorageValue
}
import ru.otus.sc.storage.service.StorageService

class StorageServiceImpl(dao: StorageDao) extends StorageService {
  def findBy(request: StorageFindRequest): StorageFindResponse =
    StorageKey
      .fromString(request.key)
      .fold(StorageFindResponse(exists = false))(
        dao
          .get(_)
          .fold(StorageFindResponse(exists = false))(v =>
            StorageFindResponse(exists = true, v.message)
          )
      )

  def findBy(request: StorageFindBulkRequest): StorageFindBulkResponse =
    StorageFindBulkResponse(
      request.keys.flatMap(k =>
        StorageKey
          .fromString(k)
          .fold(List.empty[(String, String)])(
            dao
              .get(_)
              .fold(List.empty[(String, String)])(v => (k, v.message) :: Nil)
          )
      )
    )

  def update(request: StorageUpdateRequest): StorageUpdateResponse =
    StorageKey
      .fromString(request.key)
      .fold(StorageUpdateResponse(updated = false))(k => {
        dao.insertOrUpdate(k, StorageValue(request.value))
        StorageUpdateResponse(updated = true)
      })

  def updateBulk(request: StorageUpdateBulkRequest): StorageUpdateBulkResponse =
    StorageUpdateBulkResponse({
      val proc = request.data.flatMap(d => StorageKey.fromString(d._1).map((_, StorageValue(d._2))))
      dao.insertOrUpdate(proc)
      proc.map(_._1.value)
    })

  def delete(request: StorageDeleteRequest): StorageDeleteResponse =
    StorageKey
      .fromString(request.key)
      .fold(StorageDeleteResponse(deleted = false))(k => {
        dao.delete(k)
        StorageDeleteResponse(deleted = true)
      })

  def delete(request: StorageDeleteBulkRequest): StorageDeleteBulkResponse =
    StorageDeleteBulkResponse({
      val proc = request.keys.flatMap(StorageKey.fromString)
      dao.delete(proc)
      proc.map(_.value)
    })
}
