import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;


public class Subset {
    
    public static void main(String[] args) {
        int k = 0;
        if (args.length == 1) {
            try {
                k = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                StdOut.println("Argument" + args[0] + " must be an integer.");
            }
        }
        else {
            throw new IllegalArgumentException("Usage: java subset <int>");
        }
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        String[] strings = null;
        if (!StdIn.isEmpty()) {
            strings = StdIn.readAllStrings();
        }
        int i = 0;
        while (i < k) {
            int rand = StdRandom.uniform(strings.length);
            if (strings[rand] != null) {
                queue.enqueue(strings[rand]);
                strings[rand] = null;
            }
            else
                continue;
            i++;
        }
        for (String string : queue) {
            StdOut.println(string);
        }
    }

}
