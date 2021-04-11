package ru.otus.sc.greet.model

import scala.util.control.NonFatal

class CommandException(msg: String) extends IllegalArgumentException(msg)

sealed trait Command

object Help extends Command

case class GreetRequest(
    name: String,
    isHuman: Boolean = true
) extends Command

case class EchoRequest(values: List[String]) extends Command

case class GetValueRequest(key: String) extends Command

object CounterRequest extends Command

object ExitRequest extends Command

object Command extends Enumeration {

  val help, greet, get, echo, counter, exit = Value

  def fromString(cmdStr: String): Either[String, Command] = {
    cmdStr.split("\\s+").toList match {
      case head :: tail =>
        try {
          Command.withName(head.toLowerCase) match {

            case Command.help => Right(Help)

            case Command.greet =>
              tail match {
                case name :: isHuman :: _ if isHuman.matches("[Yy]+.*") =>
                  Right(GreetRequest(name))
                case name :: _ =>
                  Right(GreetRequest(name, isHuman = false))
                case Nil =>
                  Left("missing arguments for this command, expected[greet name [y|n]")
              }

            case Command.echo =>
              Right(EchoRequest(tail))

            case Command.get =>
              if (tail.nonEmpty) {
                Right(GetValueRequest(tail.head))
              } else {
                Left("missing arguments for this command, expected[get key]")
              }

            case Command.exit =>
              Right(ExitRequest)

            case Command.counter =>
              Right(CounterRequest)

          }
        } catch {
          case NonFatal(_) => Left(s"can't understand the command received[$cmdStr]")
        }
      case Nil =>
        Left("can't understand the command received an empty line")
    }

  }
}

object app1 extends App {

  val c = Command.fromString("greet Alex y")
  println(c)

  val c1 = Command.fromString("greet Alex n")
  println(c1)

  val c11 = Command.fromString("greet")
  println(c11)

  val c2 = Command.fromString("echo Alex n")
  println(c2)

  val c3 = Command.fromString("echo")
  println(c3)

  val c4 = Command.fromString("exit asxdasx sdcsdc")
  println(c4)

  val c5 = Command.fromString("Counter")
  println(c5)

  val c6 = Command.fromString("unknown")
  println(c6)

}
