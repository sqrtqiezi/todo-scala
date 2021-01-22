package todo

class TodoTest extends BaseSpec {

  "A Todo object" should "parse a command" in {
    val todo = new Todo
    val command = todo.parse("add", "hello", "world")
    command shouldBe a [AddCommand]

  }

}
