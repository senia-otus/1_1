package ru.otus.sc.custom.model

trait Echo {}

case class EchoRequest(toEcho: String)  extends Echo
case class EchoResponse(echoed: String) extends Echo
