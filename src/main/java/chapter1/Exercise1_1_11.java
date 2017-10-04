package chapter1;

import edu.princeton.cs.algs4.StdOut;

public class Exercise1_1_11 {

    public static void main(String... args) {
        boolean[][] m = new boolean[2][2];
        m[1][0] = true;
        m[0][1] = true;
        print(m);
    }

    private static void print(boolean[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j]) {
                    StdOut.print("*");
                } else {
                    StdOut.print(" ");
                }
                StdOut.print(" ");
            }
            StdOut.println();
        }
    }

}
