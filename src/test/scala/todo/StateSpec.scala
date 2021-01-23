package todo

class StateSpec extends BaseSpec {
  var state: State = _

  before {
    state = new State
  }

  "A new State" should "have size 0" in {
    state should have size 0
  }

  "State object" should "add todo item with content and return id" in {
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

  it should "list all available items" in {
    state add "hello world"
    state add "hello scala"
    state add "hello java"
    state done 2

    val contents = state.list.map(_.content)
    contents should contain theSameElementsAs List("hello world", "hello java")
  }

  it should "list all items" in {
    state add "hello world"
    state add "hello scala"
    state add "hello java"
    state done 2

    val contents = state.listAll.map(_.content)
    contents should contain theSameElementsAs List("hello world", "hello scala", "hello java")
  }

  it should "count done items number" in {
    state add "hello world"
    state add "hello scala"
    state add "hello java"
    state done 2

    state.doneCount should equal(1)
  }

  "State" can "load data from file" in {
    import org.scalatest.TryValues.convertTryToSuccessOrFailure

    val tryState = State load "src/test/todo.dat"

    val state = tryState.success.value
    val contents = state.list.map(_.content)
    contents should contain theSameElementsAs List("hello world", "hello scala")
  }


  it should "reset id generator after load data from file" in {
    val tryState = State load "src/test/todo.dat"
    val id = tryState.get add "test2"

    id should equal(3)
  }
}
