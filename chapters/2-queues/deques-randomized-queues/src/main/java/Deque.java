import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<E> implements Iterable<E> {

  private Cell<E> head = null;
  private Cell<E> tail = null;
  private int size = 0;

  public boolean isEmpty() {
    return size() == 0;
  }

  public int size() {
    return size;
  }

  public void addFirst(E item) {
    if (item == null)
      throw new IllegalArgumentException("Item must be non-null");
    Cell<E> newHead = new Cell<>(item, null, head);
    Cell<E> oldHead = head;
    head = newHead;
    if (size() > 1) {
      assert oldHead != null;
      assert oldHead.previous == null;
      oldHead.previous = newHead;
    } else {
      tail = head;
    }
    size += 1;
  }

  public void addLast(E item) {
    if (item == null)
      throw new IllegalArgumentException("Item must be non-null");
    Cell<E> newTail = new Cell<>(item, tail, null);
    Cell<E> oldTail = tail;
    tail = newTail;
    if (size() > 1) {
      assert oldTail != null;
      assert oldTail.next == null;
      oldTail.next = newTail;
    } else { // New item is the only one
      head = tail;
    }
    size += 1;
  }

  public E removeFirst() {
    if (size() < 1) throw new NoSuchElementException("Deque is empty");
    E value = head.value;
    head = head.next;
    if (size() > 1) {
      assert head != null;
      head.previous = null;
    } else {
      tail = null;
    }
    size -= 1;
    return value;
  }

  public E removeLast() {
    if (size() < 1) throw new NoSuchElementException("Deque is empty");
    E value = tail.value;
    tail = tail.previous;
    if (size() > 1) {
      assert tail != null;
      tail.next = null;
    } else { // No elements left
      head = null;
    }
    size -= 1;
    return value;
  }

  @Override public Iterator<E> iterator() {
    return new DequeIterator();
  }

  public static void main(String[] args) {

  }

  private class DequeIterator implements Iterator<E> {

    private Cell<E> current = head;

    @Override public boolean hasNext() {
      return current != null && current.next != null;
    }

    @Override public E next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      E value = current.value;
      current = current.next;
      return value;
    }

    @Override public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  static class Cell<I> {
    final I value;
    Cell<I> previous;
    Cell<I> next;

    Cell(I value, final Cell<I> previous, Cell<I> next) {
      this.value = value;
      this.previous = previous;
      this.next = next;
    }
  }
}
