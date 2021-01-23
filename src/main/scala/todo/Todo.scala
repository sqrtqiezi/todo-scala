package todo

class Todo {
  def parse(args: Array[String]): Command = args match {
    case Array("add", content) => new AddCommand(content)
    case Array("done", idStr) =>
      val id = idStr.toInt
      new DoneCommand(id)
  }
}

object Todo {
  def main(args: Array[String]): Unit = {
    val todo = new Todo
    val state = new State

    val command = todo.parse(args)
    val message = command.execute(state)
    println(message)
  }
}
