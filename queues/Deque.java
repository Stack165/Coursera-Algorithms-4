import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private final Node<Item> head;
    private final Node<Item> tail;
    // private Node prev = null;
    private int n;

    private static class Node<Item> {
        private Item item;
        private Deque.Node<Item> next;
        private Deque.Node<Item> prev;

        private Node() {
        }
    }

    // construct an empty deque
    public Deque() {
        n = 0;
        head = new Node<>();
        tail = new Node<>();

        head.item = null;
        tail.item = null;
        head.prev = null;
        tail.prev = head;
        head.next = tail;
        tail.next = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("item is null");
        Node<Item> oldFirst = head.next;
        Node<Item> first = new Node<>();
        first.item = item;
        first.next = oldFirst;
        first.prev = head;
        oldFirst.prev = first;
        head.next = first;
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("item is null");
        Node<Item> oldLast = tail.prev;
        Node<Item> last = new Node<>();
        last.item = item;
        last.next = tail;
        last.prev = oldLast;
        tail.prev = last;
        oldLast.next = last;
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("the deque is empty.");
        Node<Item> oldFirst = head.next;
        Node<Item> first = oldFirst.next;
        Item fistItem = oldFirst.item;
        head.next = first;
        first.prev = head;
        n--;
        return fistItem;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("the deque is empty.");
        Node<Item> oldLast = tail.prev;
        Node<Item> last = oldLast.prev;
        Item item = oldLast.item;
        tail.prev = last;
        last.next = tail;
        n--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new LinkedIterator(this.head);
    }

    private class LinkedIterator implements Iterator<Item> {
        private Deque.Node<Item> current;

        public LinkedIterator(Deque.Node<Item> head) {
            this.current = head.next;
        }

        public boolean hasNext() {
            return this.current != tail;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            } else {
                Item item = this.current.item;
                this.current = this.current.next;
                return item;
            }
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            deque.addFirst(item);
        }
        StdOut.println("size of deque = " + deque.size());
        for (String s : deque) {
            StdOut.println(s);
        }
    }

}