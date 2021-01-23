package todo.command

import todo.state.State

class InvalidCommand extends Command {
  override def execute(state: State): String = throw new NotImplementedError
}
