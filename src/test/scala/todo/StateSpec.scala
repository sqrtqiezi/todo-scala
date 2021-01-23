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
    state.done(id)
    state should have size 1
  }
}
