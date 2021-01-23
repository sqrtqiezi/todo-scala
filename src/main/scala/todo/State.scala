package todo

object Status extends Enumeration {
  type Status = Value

  val available: Value = Value("AVAILABLE")
  val done: Value = Value("DONE")
}

case class Item(index: Int,
                content: String, status:
                Status.Value = Status.available)

class State {
  var count = 0
  var items: List[Item] = List[Item]()

  def add(content: String): Int = {
    count += 1
    val item = Item(count, content)
    items = items :+ item
    item.index
  }

  def done(Index: Int): Boolean = {
    var isChanged = false
    items = items.map {
      case existing@Item(Index, _, Status.available) =>
        isChanged = true
        Item(existing.index, existing.content, Status.done)
      case other => other
    }
    isChanged
  }

  def size(): Int = {
    items.count(_.status == Status.available)
  }
}

