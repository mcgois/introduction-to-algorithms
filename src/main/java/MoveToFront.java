import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class MoveToFront {

    private static final int R = 256;

    public static void encode() {
        List<Character> alphabet = createAlphabet();

        while (!BinaryStdIn.isEmpty()) {
            char current = BinaryStdIn.readChar();
            int alphaPosition = 0;
            ListIterator<Character> iterator = alphabet.listIterator();

            while (iterator.hasNext()) {
                if (iterator.next().equals(Character.valueOf(current))) {
                    BinaryStdOut.write(alphaPosition, 8);
                    char toFront = alphabet.get(alphaPosition);
                    alphabet.remove(alphaPosition);
                    alphabet.add(0, toFront);
                    break;
                }

                alphaPosition++;
            }
        }

        BinaryStdOut.close();
    }

    private static List<Character> createAlphabet() {
        List<Character> alphabet = new LinkedList<>();
        for (int i = 0; i < R; i++) {
            alphabet.add((char) i);
        }
        return alphabet;
    }

    public static void decode() {
        List<Character> alphabet = createAlphabet();
        while (!BinaryStdIn.isEmpty()) {
            int current = (int) BinaryStdIn.readChar();
            BinaryStdOut.write(alphabet.get(current), 8);
            char toFront = alphabet.remove(current);
            alphabet.add(0, toFront);
        }
        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Usage: input '+' for encoding or '-' for decoding");
        }

        if ("-".equals(args[0])) {
            MoveToFront.encode();
        } else if ("+".equals(args[0])) {
            MoveToFront.decode();
        } else {
            throw new java.lang.IllegalArgumentException("Usage: input '+' for encoding or '-' for decoding");
        }
    }

}
