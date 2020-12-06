package ru.otus.sc.greet.model

case class GreetingFilter[A](id: Id[A], greetingMethod: GreetingMethod[A])
