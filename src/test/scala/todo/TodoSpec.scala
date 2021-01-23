package todo

class TodoSpec extends BaseSpec {
  var todo: Todo = _

  before {
    todo = new Todo
  }

  it should "parse a command" in {
    val command = todo.parse("add", "hello world")

    command shouldBe a[AddCommand]
  }

  it should "parse a done command" in {
    val command = todo.parse("done", "1")
    command shouldBe a[DoneCommand]
  }
}