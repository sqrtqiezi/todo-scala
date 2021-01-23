package todo

import java.io.{File, PrintWriter}

import scala.io.Source
import scala.util.{Try, Using}

object Status extends Enumeration {
  type Status = Value

  val available: Value = Value("AVAILABLE")
  val done: Value = Value("DONE")
}

case class Item(id: Int,
                content: String, status:
                Status.Value = Status.available)

class State {
  def doneCount: Int = items.count(_.status == Status.done)

  def listAll: List[Item] = items

  def list: List[Item] = items.filter(_.status == Status.available)

  var count = 0
  var items: List[Item] = List[Item]()

  def add(content: String): Int = {
    count += 1
    val item = Item(count, content)
    add(item)
  }

  private def add(item: Item): Int = {
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

  def hibernate(path: String): Unit = {
    Using(new PrintWriter(new File(path))) { writer =>
      items foreach { case Item(id, content, status) =>
        writer.println(s"$id:$content:$status")
      }
    }
  }
}

object State {
  def load(path: String): Try[State] = {
    Using(Source.fromFile(path)) { resource =>
      val state = new State

      for (line <- resource.getLines) {
        val splits = line.split(":")
        val id = splits(0).toInt
        val content = splits(1)
        val status = Status.withName(splits(2))
        state.add(Item(id, content, status))
      }
      state.count = state.items.length

      state
    }
  }
}