import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> test = new RandomizedQueue<>();
        for (int i = 0; i < k; i++) {
            test.enqueue(StdIn.readString());
        }
        int n = k+1;
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (StdRandom.uniform(n) < k) {
                test.dequeue();
                test.enqueue(item);
            }
        }
        for (String s : test
        ) {
            System.out.println(s);
        }

    }

}
