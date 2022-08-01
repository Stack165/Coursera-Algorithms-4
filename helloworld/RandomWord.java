import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        double i = 0;
        String a = "";
        while (!StdIn.isEmpty()) {
            String b = StdIn.readString();
            i++;
            if (StdRandom.bernoulli(1 / i)) {
                a = b;
            }
        }
        StdOut.println(a);
    }
}
