package todo

import scala.util.{Failure, Success}

class Todo {
  def parse(args: Array[String]): Command = args match {
    case Array("add", content) => new AddCommand(content)
    case Array("done", idStr) =>
      val id = idStr.toInt
      new DoneCommand(id)
    case _ => new InvalidCommand
  }
}

object Todo {

  def main(args: Array[String]): Unit = {
    val todo = new Todo
    val command = todo.parse(args) match {
      case _: InvalidCommand =>
        println("invalid command")
        sys.exit(-1)
      case other => other
    }

    val state = State.load("./todo.dat") match {
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
