import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {

    private static final int R = 256;

    public static void encode() {
        // read
        String input = BinaryStdIn.readString();

        // CircularSuffixArray
        CircularSuffixArray csa = new CircularSuffixArray(input);

        // first
        for (int i = 0; i < csa.length(); i++) {
            if (csa.index(i) == 0) {
                BinaryStdOut.write(i);
                break;
            }
        }

        // print last from sorted suffix
        for (int i = 0; i < csa.length(); i++) {
            int x = (csa.index(i) + csa.length() - 1) %  csa.length();
            BinaryStdOut.write(input.charAt(x), 8);
        }

        BinaryStdOut.close();
    }

    public static void decode() {
        int first = BinaryStdIn.readInt();
        String input = BinaryStdIn.readString();
        int[] next = computeNext(input);

        int count = input.length();
        while(count > 0) {
            BinaryStdOut.write(input.charAt(next[first]), 8);
            first = next[first];
            count--;
        }

        BinaryStdOut.close();
    }

    private static int[] computeNext(String input) {
        int[] next = new int[input.length()];
        int[] count  = new int[R + 1];

        // compute count
        for (int i = 0; i < input.length(); i++) {
            count[input.charAt(i)+1]++;
        }

        // acumulado
        for (int i = 0; i < R; i++) {
            count[i+1] += count[i];
        }

        // navega de tras para a frente
        for (int i = input.length() - 1; i >= 0; i--) {
            char letter = input.charAt(i);
            int x = count[letter+1]--;
            next[x-1] = i;
        }

        return next;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Usage: input '+' for encoding or '-' for decoding");
        }

        if ("-".equals(args[0])) {
            BurrowsWheeler.encode();
        } else if ("+".equals(args[0])) {
            BurrowsWheeler.decode();
        } else {
            throw new java.lang.IllegalArgumentException("Usage: input '+' for encoding or '-' for decoding");
        }
    }
}
