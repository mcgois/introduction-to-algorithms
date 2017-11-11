import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class CircularSuffixArray {

    private final String original;
    private final Integer[] index;

    public CircularSuffixArray(String s) {
        checkNull(s);
        this.original = s;
        this.index = new Integer[s.length()];
        for (int i = 0; i < length(); i++) {
            this.index[i] = i;
        }

        Arrays.sort(index, new Comparator<Integer>() {
            @Override
            public int compare(Integer first, Integer second) {
                int firstIndex = first;
                int secondIndex = second;

                for (int i = 0; i < original.length(); i++) {
                    if (original.charAt(firstIndex) < original.charAt(secondIndex)) {
                        return -1;
                    } else if (original.charAt(firstIndex) > original.charAt(secondIndex)) {
                        return 1;
                    }

                    firstIndex = (firstIndex + 1) % original.length();
                    secondIndex = (secondIndex + 1) % original.length();
                }

                return 0;
            }
        });
    }

    public int length() {
        return original.length();
    }

    public int index(int i) {
        checkLimits(i);
        return index[i];
    }

    private void checkLimits(int i) {
        if (i < 0 || i >= original.length()) {
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray("ABRACADABRA!");
        for (int i = 0; i < circularSuffixArray.length(); i++) {
            StdOut.println(circularSuffixArray.index(i));
        }
    }

    private void checkNull(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
    }

}
