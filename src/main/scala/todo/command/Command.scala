package todo.command

import todo.state.State

abstract class Command {
  def execute(state: State): String
}

object Command {
  def apply(args: Array[String]): Command = args match {
    case Array("add", content) => new AddCommand(content)
    case Array("done", idStr) => new DoneCommand(idStr.toInt)
    case Array("list") => new ListCommand()
    case Array("list", "--all") => new ListCommand(true)
    case _ => new InvalidCommand
  }
}
