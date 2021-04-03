package ru.otus.sc.greet.ui

import ru.otus.sc.greet.model._

import scala.annotation.tailrec

trait UI {

  def run(callback: Command => ServiceResponse): Unit

  def help:String

}

object UI {
  class ConsoleUI extends UI {
    import scala.io.StdIn

    override def run(callback: Command => ServiceResponse): Unit = {

      @tailrec
      def loop(): Unit = {
        val cmdStr = StdIn.readLine()
        Command.fromString(cmdStr) match {

          case Left(value) =>
            println(s"Something wrong[$value]")
            loop()

          case Right(command) =>
            callback(command).answer match {
              case Left(value)  => println(s"Something wrong[$value]")
              case Right(value) => println(value)
            }

            command match {
              case ExitRequest => //noop
              case _           => loop()
            }

        }
      }

      println("Welcome to 1_1 application")
      println(s"Available commands: ${Command.values}")
      loop()

    }

    override def help: String = s"Available commands: ${Command.values}"

  }

  def console: ConsoleUI = new ConsoleUI

}
