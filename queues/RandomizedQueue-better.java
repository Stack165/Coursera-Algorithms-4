import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

//参考链接：https://www.cnblogs.com/mingyueanyao/p/10088467.html

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int length;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[2];
        n = 0;
        length = 2;
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
        if (n == length) resize(2 * length);
        items[n] = item;
        n++;
    }

    public void resize(int newLength) {
        Item[] temp = (Item[]) new Object[newLength];
        for (int i = 0; i < n; i++) {
            temp[i] = items[i];
        }
        items=temp;
        length=newLength;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("the deque is empty.");
        int r = StdRandom.uniform(n);
        Item randomItem = items[r];
        items[r]=items[n-1];
        items[n-1]=null;
        if (n > 0 && n <= length / 4) resize(length / 2);
        n--;
        return randomItem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("the deque is empty.");
        int r = StdRandom.uniform(n);
        return items[r];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }


    private class ArrayIterator implements Iterator<Item> {
        private int current;
        private int[] index;

        public ArrayIterator() {
            current = 0;
            index = new int[n];
            for (int i = 0; i < n; i++) {
                index[i]=i;
            }
            StdRandom.shuffle(index);
        }

        public boolean hasNext() {
            return current != n;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            } else {
                return items[index[current++]];
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
