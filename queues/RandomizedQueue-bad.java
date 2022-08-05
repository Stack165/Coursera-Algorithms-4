import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private final Node<Item> head;
    private final Node<Item> tail;
    private int n;

    private static class Node<Item> {
        private Item item;
        private RandomizedQueue.Node<Item> next;
        private RandomizedQueue.Node<Item> prev;

        private Node() {
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        n = 0;
        head = new RandomizedQueue.Node<>();
        tail = new RandomizedQueue.Node<>();

        head.item = null;
        tail.item = null;
        head.prev = null;
        tail.prev = head;
        head.next = tail;
        tail.next = null;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
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

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("the deque is empty.");
        Node<Item> temp = head.next;
        int r = StdRandom.uniform(0, n);
        for (int i = 0; i < r; i++) {
            temp = temp.next;
        }
        Item randomItem = temp.item;
        Node<Item> preTemp = temp.prev;
        Node<Item> nextTemp = temp.next;
        preTemp.next = nextTemp;
        nextTemp.prev = preTemp;
        n--;
        return randomItem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("the deque is empty.");
        Node<Item> temp = head.next;
        for (int i = 0; i < StdRandom.uniform(0, n); i++) {
            temp = temp.next;
        }
        return temp.item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new LinkedIterator(this.head);
    }


    private class LinkedIterator implements Iterator<Item> {
        private RandomizedQueue.Node<Item> current;

        public LinkedIterator(RandomizedQueue.Node<Item> head) {

            this.current = head.next;
            RandomizedQueue.Node<Item> loopNode = current;
            for (int i = 0; i < n; i++) {
                int r = i + StdRandom.uniform(n - i);
                swapItem(i, r, loopNode);
                loopNode = current.next;
            }

        }

        public boolean hasNext() {
            return this.current != tail;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        private void swapItem(int i, int random, RandomizedQueue.Node<Item> loopNode) {
            RandomizedQueue.Node<Item> chosen = loopNode;
            Item temp;
            RandomizedQueue.Node<Item> chooser = loopNode;
            for (int j = i; j <= n; j++) {
                if (j == random) {
                    chosen = loopNode;
                    break;
                }
                loopNode = loopNode.next;
            }
            temp = chosen.item;
            chosen.item = chooser.item;
            chooser.item = temp;
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
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> test = new RandomizedQueue<>();
        for (int i = 0; i < k; i++) {
            test.enqueue(StdIn.readString());
        }
        int n = k + 1;
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (StdRandom.uniform(n) < k) {
                test.dequeue();
                test.enqueue(item);
            }
        }
        for (String s : test
        ) {
            System.out.print(s + " ");
        }
        System.out.println();
        for (String s : test
        ) {
            System.out.print(s + " ");
        }
    }
}
