package todo.state

object Status extends Enumeration {
  type Status = Value

  val available: Value = Value("AVAILABLE")
  val done: Value = Value("DONE")
}
