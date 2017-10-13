package chapter1;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Exercise1_1_23 {

    static class BinarySearch {

        private BinarySearch() { }

        public static int indexOf(int[] a, int key) {
            int lo = 0;
            int hi = a.length - 1;
            while (lo <= hi) {
                // Key is in a[lo..hi] or not present.
                int mid = lo + (hi - lo) / 2;
                if      (key < a[mid]) hi = mid - 1;
                else if (key > a[mid]) lo = mid + 1;
                else return mid;
            }
            return -1;
        }

        public static void main(String[] args) {

            // read the integers from a file
            In in = new In(args[0]);
            int[] whitelist = in.readAllInts();

            boolean exclusive = "+".equals(args[1]) ? true : false;

            // sort the array
            Arrays.sort(whitelist);

            // read integer key from standard input; print if not in whitelist
            while (!StdIn.isEmpty()) {
                int key = StdIn.readInt();
                if (exclusive && BinarySearch.indexOf(whitelist, key) == -1) {
                    StdOut.println(key);
                }

                if (!exclusive && BinarySearch.indexOf(whitelist, key) != -1) {
                    StdOut.println(key);
                }
            }
        }
    }

}
