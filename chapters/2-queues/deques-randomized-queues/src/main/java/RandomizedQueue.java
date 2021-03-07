import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

  private static final int ARRAY_INITIAL_SIZE = 2;
  private Object[] items = new Object[ARRAY_INITIAL_SIZE];
  private int size = 0;

  public boolean isEmpty() {
    return size() == 0;
  }

  public int size() {
    return size;
  }

  public void enqueue(final Item item) {
    if (item == null) throw new IllegalArgumentException();

    if (size() >= items.length) { // Need to resize the array
      Object[] newItems = new Object[items.length * 2];
      System.arraycopy(items, 0, newItems, 0, items.length);
      items = newItems;
    }
    items[size()] = item;
    size += 1;
  }

  public Item dequeue() {
    if (size() < 1) throw new NoSuchElementException();

    final int indexToRemove = StdRandom.uniform(size);

    Item value = (Item) items[indexToRemove];

    final int indexOfLastItem = size() - 1;
    items[indexToRemove] = items[indexOfLastItem];
    items[indexOfLastItem] = null;
    size -= 1;

    if (size() < items.length / 4) { // Resize array
      Object[] newItems = new Object[items.length / 2];
      System.arraycopy(items, 0, newItems, 0, size());
      items = newItems;
    }
    return value;
  }

  public Item sample() {
    if (size() < 1) throw new NoSuchElementException();
    // noinspection unchecked
    return (Item) items[StdRandom.uniform(size())];
  }

  public static void main(String[] args) {
    // Only included to satisfy API requirement
  }

  @Override public Iterator<Item> iterator() {
    return new RandomizedQueueIterator<>(items, size());
  }

  private static class RandomizedQueueIterator<E> implements Iterator<E> {

    private final Object[] items;
    private int currentIndex = 0;

    RandomizedQueueIterator(final Object[] itemsToIterate, final int count) {
      items = new Object[count];
      System.arraycopy(itemsToIterate, 0, items, 0, count);
      StdRandom.shuffle(items);
    }

    @Override public boolean hasNext() {
      return currentIndex < items.length;
    }

    @Override
    public E next() {
      if (!hasNext()) throw new NoSuchElementException();
      return (E) items[currentIndex++];
    }

    @Override public void remove() {
      throw new UnsupportedOperationException();
    }
  }
}
