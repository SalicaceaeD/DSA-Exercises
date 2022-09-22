import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] st;

    /**
     * Constructor.
     */
    public RandomizedQueue() {
        st = (Item[]) new Object[2];
        size = 0;
    }

    /**
     * Return true if randomized queue is empty, otherwise false.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Return the size of randomized queue.
     */
    public int size() {
        return size;
    }

    /**
     * Resize array.
     */
    private void resize(int capacity) {
        Item[] copySt = (Item[]) new Object[capacity];
        for (int i = 0; i < size; ++i) {
            copySt[i] = st[i];
        }
        st = copySt;
    }

    /**
     * Add an item to randomized queue.
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (size == st.length) {
            resize(st.length * 2);
        }
        st[size] = item;
        ++size;
    }

    /**
     * Remove and return a random item.
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int id = StdRandom.uniformInt(size);
        --size;
        Item item = st[id];
        st[id] = st[size];
        st[size] = null;
        if (size < st.length / 4) {
            resize(st.length / 2);
        }
        return item;
    }

    /**
     * Return a random item without removing it.
     */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int id = StdRandom.uniformInt(size);
        return st[id];
    }



    /**
     * Return an independent iterator over items in random order.
     */
    public Iterator<Item> iterator() {
        return new ListIterator<>();
    }

    /**
     * Implement iterator.
     */
    private class ListIterator<Item> implements Iterator<Item> {
        private Item[] arr;
        private int arrSize;

        public ListIterator() {
            arr = (Item[]) new Object[size];
            for (int i = 0; i < size; ++i) {
                arr[i] = (Item) st[i];
            }
            arrSize = size;
        }

        public boolean hasNext() {
            return arrSize > 0;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int id = StdRandom.uniformInt(arrSize);
            Item item = arr[id];
            --arrSize;
            arr[id] = arr[arrSize];
            arr[arrSize] = null;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}