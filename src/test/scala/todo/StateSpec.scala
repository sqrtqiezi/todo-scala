package todo

class StateSpec extends BaseSpec {
  var state: State = _

  before {
    state = new State
  }

  "A new State" should "have size 0" in {
    state should have size 0
  }

  it should "add todo item with content and return id" in {
    val id = state add "hello world"
    val id2 = state add "hello scala"

    id should equal(1)
    id2 should equal(2)
    state should have size 2
  }

  it should "change the item status with id" in {
    val id = state add "hello world"
    state add "hello world"

    state should have size 2
    state done id
    state should have size 1
  }

  it can "save date to plat file" in {
    state add "hello world"
    state add "hello scala"
    state done 2
    state hibernate "src/test/todo2.dat"

    val lines = io.Source.fromFile("src/test/todo2.dat").getLines.toList
    lines should be(List("1:hello world:AVAILABLE", "2:hello scala:DONE"))
  }

  "State" can "load data from file" in {
    import org.scalatest.TryValues.convertTryToSuccessOrFailure

    val tryState = State load "src/test/todo.dat"

    tryState.success.value should have size 2
  }


  it should "reset id generator after load data from file" in {
    val tryState = State load "src/test/todo.dat"
    val id = tryState.get add "test2"

    id should equal(3)
  }
}
