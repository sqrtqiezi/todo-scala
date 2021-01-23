package todo

import java.io.{File, PrintWriter}

import scala.collection.mutable
import scala.io.Source
import scala.util.{Try, Using}

class ProxyState() extends mutable.HashMap[String, RealState] with State {
  var currentUser: Option[String] = None

  private def currentState(): RealState = {
    val key = currentUser.getOrElse("")
    this.getOrElseUpdate(key, new RealState)
  }

  def changeUser(username: String): Unit = currentUser = Option(username)

  override def add(content: String): Int = currentState().add(content)

  override def done(Id: Int): Boolean = currentState().done(Id)

  override def doneCount: Int = currentState().doneCount

  override def listAll: List[Item] = currentState().listAll

  override def list: List[Item] = currentState().list

  def hibernate(path: String): Unit = {
    Using(new PrintWriter(new File(path))) { writer =>
      keys foreach { username =>
        if (currentUser.get == username)
          writer.println(s"user:$username:true")
        else
          writer.println(s"user:$username:false")

        val items = this (username).listAll
        items foreach { case Item(id, content, status) =>
          writer.println(s"$id:$content:$status")
        }
      }
    }
  }
}

object ProxyState {
  def load(path: String): Try[ProxyState] = {
    Using(Source.fromFile(path)) { resource =>

      val proxy = new ProxyState
      var state: RealState = null
      var currentUser: String = null

      for (line <- resource.getLines) {
        line.split(":") match {
          case Array("user", username, isCurrentUser) =>
            if (isCurrentUser.toBoolean) proxy.changeUser(username)
            state = new RealState
            currentUser = username
            if (currentUser != null) {
              saveState(proxy, state, currentUser)
            }
          case Array(idStr, content, status) =>
            state.add(Item(idStr.toInt, content, Status.withName(status)))
        }
      }
      if (currentUser != null) {
        saveState(proxy, state, currentUser)
      }
      proxy
    }
  }

  private def saveState(proxy: ProxyState, state: RealState, username: String): Unit = {
    state.count = state.list.length
    proxy.put(username, state)
  }
}