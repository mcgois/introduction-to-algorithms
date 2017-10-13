package chapter1;

import edu.princeton.cs.algs4.Stopwatch;

public class Exercise1_1_27 {

    static class Binomial1 {

        public static double binomial(int N, int k, double p) {
            if ((N==0) && (k == 0)) {
                return 1.0;
            }
            if ((N < 0) || (k < 0)) {
                return 0.0;
            }
            return (1-p) * binomial(N - 1, k, p) + p * binomial(N - 1, k - 1, p);
        }

    }

    static class Binomial2 {
        public static double binomial(int N, int K, double p) {
            double[][] computed = new double[N+1][K+1];

            for (int k = 1; k <= K; k++) {
                computed[0][k] = 0.0;
            }

            for (int n = 0; n <= N; n++) {
                computed[n][0] = 1.0;
            }

            for (int n = 1; n <= N; n++) {
                for (int k = 1; k <= K; k++) {
                    computed[n][k] = (1-p) * computed[n-1][k] + p * computed[n-1][k-1];
                }
            }

            return computed[N][K];
        }
    }

    public static void main(String... args) {
        Stopwatch stopwatch = new Stopwatch();
        System.out.println(Binomial2.binomial(100, 50, 0.25));
        System.out.println(stopwatch.elapsedTime());
    }
}
