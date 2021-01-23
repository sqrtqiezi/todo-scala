package todo

import org.mockito.Mockito.{times, verify, when}
import org.scalatestplus.mockito.MockitoSugar.mock

class CommandSpec extends BaseSpec {
   "AddCommand" should "parse by args text" in {
    val command = Command(Array("add", "hello world"))

    command shouldBe a[AddCommand]
  }

  it should "add a item for state" in {
    val mockState = mock[State]
    when(mockState.add("hello world")).thenReturn(1)

    val command = new AddCommand("hello world")
    val result = command.execute(mockState)

    result should equal("1. hello world\nItem hello world added")
    verify(mockState, times(1)).add("hello world")
  }

  "DoneCommand" should "parse by args text" in {
    val command = Command(Array("done", "1"))
    command shouldBe a[DoneCommand]
  }

  it should "done a item in the state with index" in {
    val mockState = mock[State]
    when(mockState.done(2)).thenReturn(true)

    val command = new DoneCommand(2)
    val result = command.execute(mockState)

    verify(mockState, times(1)).done(2)
    result should equal("Item 2 done.")
  }

  "ListCommand" should "parse by args text" in {
    val command = Command(Array("list"))
    command shouldBe a[ListCommand]
  }

  it should "list all available items default" in {
    val mockState = mock[State]
    when(mockState.list).thenReturn(List(
      Item(1, "hello world", Status.available),
      Item(2, "hello scala", Status.available)
    ))

    val command = Command(Array("list"))
    val result = command.execute(mockState)

    verify(mockState, times(1)).list
    result should equal("1. hello world\n2. hello scala\nTotal: 2 items")
  }

  it should "parse by args text with --all argument" in {
    val command = Command(Array("list", "--all"))

    val listCommand = command.asInstanceOf[ListCommand]
    listCommand.isShowAll should equal(true)
  }

  it should "list all items when given argument --all" in {
    val mockState = mock[State]
    when(mockState.listAll).thenReturn(List(
      Item(1, "hello world", Status.available),
      Item(2, "hello scala", Status.done),
      Item(3, "hello java", Status.available),
    ))
    when(mockState.doneCount).thenReturn(1)

    val command = Command(Array("list", "--all"))
    val result = command.execute(mockState)

    verify(mockState, times(1)).listAll
    verify(mockState, times(1)).doneCount
    result should equal("1. hello world\n2. [Done]hello scala\n3. hello java\n" +
      "Total: 3 items, 1 item done")
  }
}
