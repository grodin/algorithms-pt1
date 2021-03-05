import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class DequeTest {

  @Test
  void newDequeIsEmpty() {
    assertTrue(new Deque<>().isEmpty());
  }

  @Test
  void newDequeHasSizeZero() {
    assertEquals(0, new Deque<>().size());
  }

  @Test()
  void removeFirstOnEmptyDequeThrowsNoSuchElementException() {
    Deque<Object> emptyDeque = new Deque<>();
    assertThrows(NoSuchElementException.class, emptyDeque::removeFirst);
  }

  @Test()
  void removeLastOnEmptyDequeThrowsNoSuchElementException() {
    Deque<Object> emptyDeque = new Deque<>();
    assertThrows(NoSuchElementException.class, emptyDeque::removeLast);
  }

  @Test
  void addFirstWithNullArgumentThrowsIAE() {
    Deque<Object> emptyDeque = new Deque<>();
    assertThrows(IllegalArgumentException.class, () -> {
      emptyDeque.addFirst(null);
    });
  }

  @Test
  void addLastWithNullArgumentThrowsIAE() {
    Deque<Object> emptyDeque = new Deque<>();
    assertThrows(IllegalArgumentException.class, () -> {
      emptyDeque.addLast(null);
    });
  }


}
