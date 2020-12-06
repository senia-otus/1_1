package ru.otus.sc.greet

case class GreetingConfig(prefix: String, postfix: String)
case class Config(bots: List[String], greeting: GreetingConfig)
