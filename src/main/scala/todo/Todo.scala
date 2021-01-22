package todo

import scala.language.postfixOps

class Todo {
  def parse(args: String*): Command = args toList match {
    case "add" :: content :: Nil =>
      AddCommand(content)
  }
}

object Todo {
  def main(args: Array[String]): Unit = {
    val todo = new Todo

    todo.parse(args:_*)
  }
}
