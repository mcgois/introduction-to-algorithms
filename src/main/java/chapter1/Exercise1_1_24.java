package chapter1;

import edu.princeton.cs.algs4.StdOut;

public class Exercise1_1_24 {

    public static int gcd(int p, int q) {
        StdOut.printf("[%d, %d]\n", p, q);
        if (q == 0) return p;
        int r = p % q;
        return gcd(q, r);
    }

    public static void main(String... args) {
        StdOut.println(gcd(1111111, 1234567));
    }

}
