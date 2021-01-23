package todo

import org.scalatest.TryValues.convertTryToSuccessOrFailure

import scala.collection.immutable.List

class ProxyStateSpec extends BaseSpec {
  var proxy: ProxyState = _

  before {
    proxy = new ProxyState
  }

  "ProxyState" should "be a Map[String, State]" in {
    proxy.put("njin", new RealState)
    proxy.put("qiezi", new RealState)

    proxy.keys.toList should contain theSameElementsAs List("njin", "qiezi")
  }

  it should "proxy all public method of State" in {
    proxy shouldBe a[State]
    val id = proxy.add("hello world")
    id should equal(1)

    proxy.list should contain theSameElementsAs List(Item(1, "hello world"))

    val isChanged = proxy.done(id)
    isChanged should equal(true)

    proxy.listAll should contain theSameElementsAs List(Item(1, "hello world", Status.done))
  }

  it should "change todo list when change current user" in {
    proxy changeUser "njin"
    proxy add "hello world"
    proxy add "hello scala"
    proxy changeUser "qiezi"
    proxy add "hello java"

    proxy.listAll should contain theSameElementsAs List(
      Item(1, "hello java")
    )
    proxy changeUser "njin"
    proxy.listAll should contain theSameElementsAs List(
      Item(1, "hello world"),
      Item(2, "hello scala")
    )
  }

  it can "load data from file" in {
    val tryProxyState = ProxyState load "src/test/todo.dat"
    val proxyState = tryProxyState.success.value

    proxyState.currentUser.get should equal("njin")
    proxyState.list should contain theSameElementsAs List(
      Item(1, "hello world"),
      Item(2, "hello scala")
    )
  }

  it should "reset id generator after load data from file" in {
    val tryProxyState = ProxyState load "src/test/todo.dat"
    val proxyState = tryProxyState.success.value

    val id = proxyState add "test2"

    id should equal(3)
  }

  it can "save data to plat file" in {
    proxy changeUser "njin"
    proxy add "hello world"
    proxy add "hello scala"
    proxy done 2
    proxy changeUser "qiezi"
    proxy add "hello java"
    proxy hibernate "src/test/todo2.dat"

    val lines = io.Source.fromFile("src/test/todo2.dat").getLines.toList
    lines should be(List(
      "user:njin:false",
      "1:hello world:AVAILABLE",
      "2:hello scala:DONE",
      "user:qiezi:true",
      "1:hello java:AVAILABLE"
    ))
  }
}
