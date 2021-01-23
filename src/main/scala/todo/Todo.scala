package todo

import scala.language.postfixOps

class Todo {
  def parse(args: String*): Command = args toList match {
    case "add" :: content :: Nil => new AddCommand(content)
    case "done" :: idStr :: Nil =>
      val id = idStr.toInt
      new DoneCommand(id)
  }
}

object Todo {
  def main(args: Array[String]): Unit = {
    val todo = new Todo

    todo.parse(args: _*)
  }
}
