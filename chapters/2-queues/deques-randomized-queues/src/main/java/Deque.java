import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

  private Cell<Item> head = null;
  private Cell<Item> tail = null;
  private int size = 0;

  public boolean isEmpty() {
    return size() == 0;
  }

  public int size() {
    return size;
  }

  public void addFirst(Item item) {
    if (item == null)
      throw new IllegalArgumentException("Item must be non-null");
    Cell<Item> newHead = new Cell<>(item, null, head);
    Cell<Item> oldHead = head;
    head = newHead;
    if (size() > 0) {
      assert oldHead != null;
      assert oldHead.previous == null;
      oldHead.previous = newHead;
    } else {
      tail = head;
    }
    size += 1;
  }

  public void addLast(Item item) {
    if (item == null)
      throw new IllegalArgumentException("Item must be non-null");
    Cell<Item> newTail = new Cell<>(item, tail, null);
    Cell<Item> oldTail = tail;
    tail = newTail;
    if (size() > 0) {
      assert oldTail != null;
      assert oldTail.next == null;
      oldTail.next = newTail;
    } else { // New item is the only one
      head = tail;
    }
    size += 1;
  }

  public Item removeFirst() {
    if (size() < 1) throw new NoSuchElementException("Deque is empty");
    Item value = head.value;
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

  public Item removeLast() {
    if (size() < 1) throw new NoSuchElementException("Deque is empty");
    Item value = tail.value;
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

  @Override public Iterator<Item> iterator() {
    return new DequeIterator();
  }

  public static void main(String[] args) {
    Deque<Integer> deque = new Deque<>();

    StdOut.println("Newly created Deque size: " + deque.size());

    deque.addFirst(0);
    deque.addLast(1);
    deque.addLast(2);
    deque.addFirst(-1);

    StdOut.println("Deque now has size: " + deque.size());
    StdOut.println("Deque is empty? " + deque.isEmpty());
    StdOut.print("Deque contains :");
    deque.iterator().forEachRemaining((i) -> StdOut.println(i));

    StdOut.println("Deque last element was: " + deque.removeLast());
    StdOut.println("Deque first element was: " + deque.removeFirst());

    StdOut.println("Iterating Deque...");
    deque.iterator().forEachRemaining((i) -> StdOut.println(i));
    StdOut.println("Done");
  }

  private class DequeIterator implements Iterator<Item> {

    private Cell<Item> current = head;

    @Override public boolean hasNext() {
      return current != null;
    }

    @Override public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      Item value = current.value;
      current = current.next;
      return value;
    }

    @Override public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  private static class Cell<I> {
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
