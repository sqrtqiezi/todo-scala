package todo

class TodoTest extends BaseSpec {
  var todo: Todo = _

  before {
    todo = new Todo
  }

  "A Todo object" should "parse a command" in {
    val command = todo.parse("add", "hello world")

    command shouldBe a [AddCommand]
  }

  it should "parse a done command" in {
    val command = todo.parse("done", "1")
    command shouldBe a [DoneCommand]
  }

}
