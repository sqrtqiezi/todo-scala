package todo

trait State {
  def add(content: String): Int

  def done(Id: Int): Boolean

  def size(): Int

  def doneCount: Int

  def listAll: List[Item]

  def list: List[Item]
}
