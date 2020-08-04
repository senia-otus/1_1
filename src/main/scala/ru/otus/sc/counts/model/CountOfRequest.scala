package ru.otus.sc.counts.model

import ru.otus.sc.utils.model.RequireKey

/**
  * Request for get the call count by key
  *
  * @param key
  */
case class CountOfRequest(key: String) extends RequireKey
