package chapter1;

import edu.princeton.cs.algs4.StdOut;

public class Exercise1_1_20 {

    public static void main(String... args) {
        StdOut.println(func(20));
    }

    // ln(n*x) = ln(n) + ln(x)
    // ln(1) = 0
    private static double func(int n) {
        if (n == 1) {
            return 0;
        }
        return func(n-1) + Math.log(n);
    }

}
