import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] q;
//    private int n = 0;
//    private int head = 0;
    private int tail = 0;

    public RandomizedQueue() {
        q = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return tail == 0;
    }

    public int size() {
        return tail;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("no null elements");
        }

        if (tail == q.length) {
            resize(2 * q.length);
        }
        q[tail++] = item;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < tail; i++) {
            copy[i] = q[i];
        }
        q = copy;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("no element to remove");
        }
        int indexToRemove = StdRandom.uniform(0, tail);
        Item item = q[indexToRemove];
        q[indexToRemove] = q[tail-1];
        q[tail-1] = null;
        tail--;
        if (tail > 0 && tail == q.length / 4) {
            resize(q.length / 2);
        }
        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("no element to sample");
        }
        int index = StdRandom.uniform(0, tail);
        return q[index];
    }

    @Override
    public Iterator<Item> iterator() {
        return new ShuffleIterator();
    }

    private class ShuffleIterator implements Iterator<Item> {

        private Item[] qCopy;
        private int current = 0;

        ShuffleIterator() {
            qCopy = (Item[]) new Object[q.length];
            for (int i = 0; i < tail; i++) {
                qCopy[i] = q[i];
            }
            if (0 < tail) {
                StdRandom.shuffle(qCopy, 0, tail - 1);
            }
        }

        @Override
        public boolean hasNext() {
            return current < tail;
        }

        @Override
        public Item next() {
            if (current >= tail) {
                throw new NoSuchElementException("no such element.");
            }
            return qCopy[current++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("invalid operation");
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        queue.enqueue("1");
        queue.enqueue("2");
        queue.enqueue("3");
        queue.enqueue("4");
        queue.enqueue("5");
        System.out.println(queue.dequeue());
        queue.enqueue("6");
        queue.enqueue("7");
        queue.enqueue("8");
        queue.enqueue("9");
        queue.enqueue("10");
        System.out.println("uia : " + queue.sample());

        for (String str : queue) {
            System.out.println(str);
        }

    }

}
