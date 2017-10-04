package chapter1;

import edu.princeton.cs.algs4.StdOut;

public class Exercise1_1_19 {

    public static void main(String... args) {
        // solution 1
        //        for (int N = 0; N < 100; N++) {
        //            StdOut.println(N + " " + F(N));
        //        }

        // solution 2
        int[] a = new int[100];
        F2(a);
        StdOut.println(a[99]);

    }

    private static int F(int N) {
        if (N == 0) {
            return 0;
        }
        if (N == 1) {
            return 1;
        }
        return F(N-1) + F(N-2);
    }

    private static void F2(int[] a) {
        a[0] = 0;
        a[1] = 1;
        for(int i = 2; i < a.length; i++) {
            a[i] = a[i-1] + a[i-2];
        }
    }

}
