package ru.otus.sc.store.model

import ru.otus.sc.utils.model.RequireKey

/**
  * Request to get containing value by existing key
  *
  * @param key existing key
  */
case class StoreGetRequest(key: String) extends RequireKey
