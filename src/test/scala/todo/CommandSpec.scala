package todo

class CommandSpec extends BaseSpec {

  "An AddCommand" should "add a item for state" in {
    val state = new State
    val command = AddCommand("hello world")
    val result = command.execute(state)

    result should equal("1. hello world\nItem hello world added")
    state should have size 1
  }
}
