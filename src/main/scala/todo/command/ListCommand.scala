package todo.command

import todo.state.{Item, State, Status}

class ListCommand(val isShowAll: Boolean = false) extends Command {
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