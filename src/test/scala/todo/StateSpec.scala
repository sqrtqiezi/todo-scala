package todo

class StateSpec extends BaseSpec {
  var state: RealState = _

  before {
    state = new RealState
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
}
