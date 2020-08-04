package ru.otus.sc.counts.model

import ru.otus.sc.utils.model.RequireKey

/**
  * Request for get the call count by key
  *
  * @param key name of call counter
  */
case class CountOfRequest(key: String) extends RequireKey
