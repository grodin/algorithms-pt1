import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;
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

  @Test
  void dequeIteratorThrowsNoSuchElementExceptionWhenNoMoreElements() {
    Deque<Integer> intDeque = new Deque<>();
    for (int i = 0; i < 2; i++) {
      intDeque.addLast(i);
    }
    Iterator<Integer> iterator = intDeque.iterator();

    iterator.next();
    iterator.next();

    assertThrows(NoSuchElementException.class, () -> iterator.next());
  }

  @Test
  void interleavedAddsWorkCorrectly() {
    Deque<Integer> intDeque = new Deque<>();
    intDeque.addFirst(0);
    intDeque.addLast(1);
    intDeque.addFirst(-1);
    intDeque.addLast(2);

    assertIterableEquals(List.of(-1, 0, 1, 2), intDeque);
  }

  @Test
  void dequeIteratorCorrectlyIterates() {
    Deque<Integer> intDeque = new Deque<>();
    for (int i = 0; i < 4; i++) {
      intDeque.addLast(i);
    }

    Iterable<Integer> expected = List.of(0, 1, 2, 3);

    assertIterableEquals(expected, intDeque);
  }
}
