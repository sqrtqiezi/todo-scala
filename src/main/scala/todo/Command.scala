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

case class ListCommand(isShowAll: Boolean = false) extends Command {
  override def execute(state: State): String = {
    val builder = new StringBuilder
    val items = if (isShowAll) state.listAll else state.list

    items.foreach {
      case Item(id, content, Status.available) => builder.append(s"$id. $content\n")
      case Item(id, content, Status.done) => builder.append(s"$id. [Done]$content\n")
    }

    builder.append(s"Total: ${items.length} items")
    if (isShowAll) builder.append(s", ${state.doneCount} item done")

    builder.toString
  }
}

object Command {
  def apply(args: Array[String]): Command = args match {
    case Array("add", content) => new AddCommand(content)
    case Array("done", idStr) =>
      val id = idStr.toInt
      new DoneCommand(id)
    case Array("list") => ListCommand()
    case Array("list", "--all") => ListCommand(true)
    case _ => new InvalidCommand
  }
}