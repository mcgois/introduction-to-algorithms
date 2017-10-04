package chapter1;

import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Exercise1_1_22 {

    public static int rank(int key, int[] a) {
        return rank(key, a, 0, a.length - 1, 0);
    }

    private static int rank(int key, int[] a, int lo, int hi, int depth) {
        int temp = depth;

        //ident
        while(temp > 0) {
            StdOut.print(" ");
            temp--;
        }

        StdOut.printf("[%d, %d]\n", lo, hi);

        if (lo > hi) return -1;
        int mid = lo + (hi - lo) / 2;
        if (key < a[mid]) {
            return rank(key, a, lo, mid - 1, depth + 1);
        }
        else if (key > a[mid]) {
            return rank(key, a, mid + 1, hi, depth + 1);
        }
        else {
            return mid;
        }
    }

    public static void main(String... args) {
        int[] a = new int[]{1, 2, 5, 6, 7, 8, 10, 50};
        StdOut.println("Found at index: " + rank(7, a));
    }

}
