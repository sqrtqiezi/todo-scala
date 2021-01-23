package todo

import todo.state.ProxyState

import scala.util.{Failure, Success}

object Todo {
  def main(args: Array[String]): Unit = {
    val command = Command(args) match {
      case _: InvalidCommand =>
        println("Invalid Command")
        sys.exit(-1)
      case other => other
    }

    val state = ProxyState.load("./todo.dat") match {
      case Success(state) => state
      case Failure(exception) =>
        println(exception)
        sys.exit(-1)
    }

    val message = command.execute(state)

    state.hibernate("./todo.dat")
    println(message)
  }
}
