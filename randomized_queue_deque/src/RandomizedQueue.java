import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

/*----------------------------------------------------------------
 *  Author:        Mike Milonakis
 *  Written:       23/09/2015
 *  Last updated:  23/09/2015
 *
 *  Compilation:   javac RandomizedQueue.java 
 *  Execution:     java RandomizedQueue
 *  
 * 
 *  
 *  This is the second programming assignment of 
 *  princeton - algorithms course in coursera
 *
 *----------------------------------------------------------------*/

/**
 * A randomized queue is similar to a stack or queue, except that the item 
 * removed is chosen uniformly at random from items in the
 * data structure.
 */

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int index = 0;
    private Item[] queue;

    /**
     * Default Constructor
     */
    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
    }

    /**
     * checks if the queue is empty
     * @return
     */
    public boolean isEmpty() {
        return index == 0;
    }

    /**
     * returns the number of items in the queue
     * @return the number of items
     */
    public int size() {
        return index;
    }

    /**
     * Add an item in the queue 
     * 
     * @param item the item to be added
     */
    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException();
        if (index == queue.length)
            resizeArray(2 * queue.length);
        queue[index++] = item;
    }

    /**
     * Return and remove a random item from the queue
     * 
     * @return the random item
     */
    public Item dequeue() {
        if (queue[0] == null)
            throw new NoSuchElementException();
        int randomIndex = StdRandom.uniform(index);
        Item item = queue[randomIndex];
        queue[randomIndex] = queue[--index];
        queue[index] = null;
        if (index > 0 && index == queue.length / 4)
            resizeArray(queue.length / 2);
        return item;
    }

    /**
     * Return a random item from the queue
     * 
     * @return a random item
     */
    public Item sample() {
        if (queue[0] == null)
            throw new NoSuchElementException();
        return queue[StdRandom.uniform(index)];
    }

    /**
     * Returns an iterator
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private void resizeArray(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < index; i++) {
            copy[i] = queue[i];
        }
        queue = copy;

    }

    private void exchange(Object[] array, int i, int r) {
        Object temp = array[i];
        array[i] = array[r];
        array[r] = temp;
    }

    /**
     * Test client
     * @param args
     */
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        queue.enqueue(6);
        
        for (int integer : queue) {
            System.out.println(integer);
        } 
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private final Item[] array;
        private int current = 0;

        private RandomizedQueueIterator() {
            array = (Item[]) new Object[index];
            for (int i = 0; i < index; i++) {
                array[i] = queue[i];
                int r = StdRandom.uniform(i + 1);
                exchange(array, i, r);
            }
        }

        @Override
        public boolean hasNext() {
            return (current != array.length);
        }

        @Override
        public Item next() {
            if (hasNext())
                return array[current++];
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
