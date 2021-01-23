package todo.state

case class Item(id: Int,
                content: String,
                status: Status.Value = Status.available)
