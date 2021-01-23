package todo.state

class RealState extends State {
  var count = 0
  var items: List[Item] = List[Item]()


  def doneCount: Int = items.count(_.status == Status.done)

  def listAll: List[Item] = items

  def list: List[Item] = items.filter(_.status == Status.available)

  def add(content: String): Int = {
    count += 1
    val item = Item(count, content)
    add(item)
  }

  def add(item: Item): Int = {
    items = items :+ item
    item.id
  }

  def done(Id: Int): Boolean = {
    var isChanged = false
    items = items.map {
      case existing@Item(Id, _, Status.available) =>
        isChanged = true
        Item(existing.id, existing.content, Status.done)
      case other => other
    }
    isChanged
  }

  def size(): Int = {
    items.count(_.status == Status.available)
  }
}