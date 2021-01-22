package todo

class StateSpec extends BaseSpec {
  var state: State = _

  before {
    state = new State
  }

  "A new State" should "have size 0" in {
    state should have size 0
  }

  it should "add todo item with content and return index" in {
    val index = state add "hello world"
    val index2 = state add "hello scala"

    index should equal(1)
    index2 should equal(2)
    state should have size 2
  }
}
