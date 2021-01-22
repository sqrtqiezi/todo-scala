package todo

case class Item(index: Int, content: String)

class State {
  var count = 0
  var items: List[Item] = List[Item]()

  def add(content: String): Int = {
    count += 1
    val item = Item(count, content)
    items = items :+ item
    item.index
  }

  def size(): Int = {
    items.size
  }
}

