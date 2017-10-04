package chapter1;

import edu.princeton.cs.algs4.StdOut;

public class Exercise1_1_13 {

    public static void main(String... args) {
        int[][] m = new int[2][2];
        m[1][0] = 2;
        m[0][1] = 3;
        m[1][1] = 4;
        transpose(m);
    }

    private static void transpose(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                StdOut.print(matrix[j][i]);
                StdOut.print(" ");
            }
            StdOut.println();
        }
    }


}
