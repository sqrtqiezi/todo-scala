package todo

sealed abstract class Command {
  def execute(state: State): String
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
