package ru.otus.sc.storage.model

/**
 * Response for storage service
 * @param item handled pair from storage
 * @param message message with exception
 */
case class StorageResponse (item: (String, String), message: String = "")
