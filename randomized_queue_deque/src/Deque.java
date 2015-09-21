import java.util.Iterator;
import java.util.NoSuchElementException;


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
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public int size() {
        return size;
    }
    
    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException();
        final Node oldfirst = first;
        first = new Node(item);
        first.next = oldfirst;
        first.prev = null;
        if (oldfirst == null) 
            last = first;
        size++;
    }
    
    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException();
        final Node oldlast = last;
        last = new Node(item);
        oldlast.next = last;
        last.prev = oldlast;
        size++;
    }
    
    public Item removeFirst(Item item) {
        if (size == 0)
            throw new NoSuchElementException();
        final Node oldFirst = first;
        oldFirst.next.prev = null;
        first = first.next;
        size--;
        return oldFirst.item;
    }
    
    public Item removeLast(Item item) {
        if (size == 0)
            throw new NoSuchElementException();
        final Node oldLast = last;
        last = oldLast.prev;
        last.next = null;
        size--;
        return oldLast.item;
    }

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
            Item item = current.item;
            current = current.next;
            return item;
        }

    }
    
}
