package todo.command

import todo.state.State

class AddCommand(content: String) extends Command {
  override def execute(state: State): String = {
    val id = state.add(content)

    s"$id. $content\nItem $content added"
  }
}
