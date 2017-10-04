package chapter1;

import java.util.Arrays;

public class Exercise1_1_15 {

    public static void main(String... args) {
        int[] a = new int[] { 2, 2, 1, 4, 3 };
        System.out.println(Arrays.toString(histogram(a, 10)));
    }

    private static int[] histogram(int[] a, int M) {
        int[] result = new int[M];

        for (int i = 0; i < M; i++) {
            int count = 0;
            for (int j = 0; j < a.length; j++) {
                if (a[j] == i) {
                    count++;
                }
            }
            result[i] = count;
        }

        return result;
    }

}
