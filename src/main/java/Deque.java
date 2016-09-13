import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int size = 0;

    private class Node {
        private Item item;
        private Node next;
        private Node previous;
    }

    public Deque() {

    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("no null elements");
        }

        if (isEmpty()) {
            first = new Node();
            first.item = item;
            first.next = null;
            first.previous = null;
            last = first;
        } else {
            Node oldfirst = first;
            first = new Node();
            first.item = item;
            first.next = oldfirst;
            first.previous = null;
            oldfirst.previous = first;
        }

        size++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("no null elements");
        }

        if (isEmpty()) {
            last = new Node();
            last.item = item;
            last.next = null;
            last.previous = null;
            first = last;
        } else {
            Node oldlast = last;
            last = new Node();
            last.item = item;
            last.next = null;
            last.previous = oldlast;
            oldlast.next = last;
        }
        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("no element to remove");
        }
        Item item = first.item;
        first = first.next;
        if (isEmpty()) {
            last = null;
        } else {
            first.previous = null;
        }
        size--;
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("no element to remove");
        }
        Item item = last.item;
        last = last.previous;
        if (last == null) {
            first = null;
        } else {
            last.next = null;
        }
        size--;
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (current == null) {
                throw new NoSuchElementException("no such element.");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("unsupported operation.");
        }
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        deque.addFirst("Matheus");
        System.out.println(deque.iterator().next());
//        deque.addFirst("Cabral");
//        deque.addFirst("Matheus");
//        deque.addLast("Gois");
//        System.out.println(deque.removeLast());
//        System.out.println("uia");
    }

}
