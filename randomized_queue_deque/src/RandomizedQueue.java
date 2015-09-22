import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;


public class RandomizedQueue<Item> {
    
    private int index = 0;
    private Item[] queue;
    
    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
    }
    
    public boolean isEmpty() {
        return index == 0;
    }
    
    public int size() {
        return index;
    }
    
    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException();
        if (index == queue.length)
            resizeAndShuffle(2 * queue.length);
        queue[index++] = item;
    }
    
    public Item dequeue() {
        if (queue[0] == null)
            throw new NoSuchElementException();
        Item item = queue[--index];
        queue[index] = null;
        if (index > 0 && index == queue.length / 4)
            resizeAndShuffle(queue.length/2);
        return item;
    }
    
    
    
    private void resizeAndShuffle(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < index; i++) {
            int r = StdRandom.uniform(i+1);
            exchange(queue, i, r);
            copy[i] = queue[i];
        }
        queue = copy;
        
    }
//
//    private void shuffle(Item[] queue) {
//        for (int i=0; i<index; i++) {
//            int r = StdRandom.uniform(i+1);
//            exchange(queue, i, r);
//        }
//    }

    private void exchange(Object[] array, int i, int r) {
        Object temp = array[i];
        array[i] = array[r];
        array[r] = temp;
    }
    
    

}
