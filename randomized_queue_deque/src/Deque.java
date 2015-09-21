import java.util.Iterator;
import java.util.NoSuchElementException;

/*----------------------------------------------------------------
 *  Author:        Mike Milonakis
 *  Written:       21/09/2015
 *  Last updated:  21/09/2015
 *
 *  Compilation:   javac Deque.java 
 *  Execution:     java Deque
 *  
 * 
 *  
 *  This is the second programming assignment of 
 *  princeton - algorithms course in coursera
 *
 *----------------------------------------------------------------*/

/**
 * A double-ended queue or deque (pronounced "deck") is a generalization of a 
 * stack and a queue that supports adding and removing
 * items from either the front or the back of the data structure 
 * 
 */

public class Deque<Item> implements Iterable<Item> {

    private Node first = null;
    private Node last = null;
    private int size = 0;

    private class Node {

        private Node(Item item) {
            this.item = item;
        }

        Item item;
        Node next;
        Node prev;
    }

    /**
     * Checks if deque is empty 
     * 
     * @return True if deque is empty, else otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of items in the deque
     * 
     * @return the number of items
     */
    public int size() {
        return size;
    }

    /**
     * Adds item at the front of deque
     * 
     * @param item the item to be added
     */
    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException();
        final Node oldFirst = first;
        first = new Node(item);
        if (size == 0) {
            first.next = null;
            first.prev = null;
            last = first;
        } else {
            first.next = oldFirst;
            oldFirst.prev = first;
            first.prev = null;
            if (size == 1)
                last.prev = oldFirst;
        }
        size++;
    }

    /**
     * Adds item at the last of deque
     * 
     * @param item the item to be added
     */
    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException();
        final Node oldlast = last;
        last = new Node(item);
        if (size == 0) {
            last.next = null;
            last.prev = null;
            first = last;
        } else {
            oldlast.next = last;
            last.prev = oldlast;
            last.next = null;
            if (size == 1)
                first.next = last;
        }
        size++;
    }

    /**
     * Removes and returns the item from the front
     * 
     * @return the first item of the deque
     */
    public Item removeFirst() {
        if (size == 0)
            throw new NoSuchElementException();
        final Node oldFirst = first;
        if (size == 1) {
            first = null;
            last = null;
        } else {
            first = oldFirst.next;
            first.prev = null;
            if (size == 2) {
                first = last;
                last.prev = null;
            }
        }
        size--;
        return oldFirst.item;
    }

    /**
     * Removes and returns the item from the end
     * 
     * @return the last item of the deque
     */
    public Item removeLast() {
        if (size == 0)
            throw new NoSuchElementException();
        final Node oldLast = last;
        if (size == 1) {
            first = null;
            last = null;
        } else {
            last = oldLast.prev;
            last.next = null;
            if (size == 2) {
                first = last;
                first.next = null;
            }
        }
        size--;
        return oldLast.item;
    }

    /**
     * Returns an iterator over items, in order from front to end
     * 
     */
    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();

        System.out.println(deque.size);

        System.out.println(deque.iterator().hasNext());

        deque.addFirst(2);
        deque.addFirst(3);
        System.out.println(deque.removeFirst());
        deque.addLast(1);
        deque.addFirst(4);

        System.out.println(deque.removeLast());

        System.out.println(deque.removeLast());

        System.out.println(deque.removeLast());

        try {
            System.out.println(deque.removeLast());
        } catch (NoSuchElementException e) {
            System.out.println("No Such Element!");
        }
    }
}
