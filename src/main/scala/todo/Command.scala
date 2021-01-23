package todo

sealed abstract class Command {
  def execute(state: State): String
}

class InvalidCommand extends Command {
  override def execute(state: State): String = throw new NotImplementedError
}

class AddCommand(content: String) extends Command {
  override def execute(state: State): String = {
    val id = state.add(content)

    s"$id. $content\nItem $content added"
  }
}

class DoneCommand(index: Int) extends Command {
  override def execute(state: State): String = {
    if (state.done(index))
      s"Item $index done."
    else
      "Nothing changed"
  }
}

object Command {
  def apply(args: Array[String]): Command = args match {
    case Array("add", content) => new AddCommand(content)
    case Array("done", idStr) =>
      val id = idStr.toInt
      new DoneCommand(id)
    case _ => new InvalidCommand
  }
}