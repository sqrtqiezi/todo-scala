package todo

sealed abstract class Command {
  def execute(state: State): String
}

case class AddCommand(content: String) extends Command {
  override def execute(state: State): String = {
    val index = state.add(content)

    s"$index. $content\nItem $content added"
  }
}
