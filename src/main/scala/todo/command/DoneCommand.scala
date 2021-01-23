package todo.command

import todo.state.State

class DoneCommand(index: Int) extends Command {
  override def execute(state: State): String = {
    if (state.done(index))
      s"Item $index done."
    else
      "Nothing changed"
  }
}
