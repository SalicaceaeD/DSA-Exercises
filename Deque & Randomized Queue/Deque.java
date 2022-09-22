import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size = 0;

    /**
     * Doubly linked list.
     */
    private class Node {
        Item item;
        Node prev;
        Node next;

        Node() {

        }

        /**
         * Constructor with argument item.
         */
        Node(Item item) {
            this.item = item;
        }

        /**
         * Connect two nodes (this -> other).
         * @param other the other node
         */
        void connect(Node other) {
            this.next = other;
            other.prev = this;
        }
    }

    /**
     * Constructor.
     */
    public Deque() {

    }

    /**
     * Return true if deque is empty, otherwise false.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Return the size of deque.
     */
    public int size() {
        return size;
    }

    /**
     * Add new item to the front.
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldFirst = first;
        first = new Node(item);
        if (oldFirst != null) {
            first.connect(oldFirst);
        }
        if (isEmpty()) {
            last = first;
        }
        ++size;
    }

    /**
     * Add new item to the back.
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldLast = last;
        last = new Node(item);
        if (oldLast != null) {
            oldLast.connect(last);
        }
        if (isEmpty()) {
            first = last;
        }
        ++size;
    }

    /**
     * Remove and return the item from the front.
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        first = first.next;
        --size;
        if (!isEmpty()) {
            first.prev = null;
        } else {
            last = null;
        }
        return item;
    }

    /**
     * Remove and return the item from the back.
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = last.item;
        last = last.prev;
        --size;
        if (!isEmpty()) {
            last.next = null;
        } else {
            first = null;
        }
        return item;
    }

    /**
     * Return an iterator over items in order from front to back.
     */
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    /**
     * Implement iterator.
     */
    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() {
            return current != null;
        }
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
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