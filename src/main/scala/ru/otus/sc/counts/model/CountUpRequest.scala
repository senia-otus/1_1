package ru.otus.sc.counts.model

import ru.otus.sc.utils.model.RequireKey

/**
  * Request to increment call counter
  *
  * @param key name of call counter
  */
case class CountUpRequest(key: String) extends RequireKey
