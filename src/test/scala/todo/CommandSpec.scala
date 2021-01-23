package todo

import org.mockito.Mockito.{times, verify, when}
import org.scalatestplus.mockito.MockitoSugar.mock

class CommandSpec extends BaseSpec {

  "An AddCommand" should "add a item for state" in {
    val mockState = mock[State]
    when(mockState.add("hello world")).thenReturn(1)

    val command = new AddCommand("hello world")
    val result = command.execute(mockState)

    result should equal("1. hello world\nItem hello world added")
    verify(mockState, times(1)).add("hello world")
  }

  "A DoneCommand" should "done a item in the state with index" in {
    val mockState = mock[State]
    when(mockState.done(2)).thenReturn(true)

    val command = new DoneCommand(2)
    val result = command.execute(mockState)

    verify(mockState, times(1)).done(2)
    result should equal("Item 2 done.")
  }
}
