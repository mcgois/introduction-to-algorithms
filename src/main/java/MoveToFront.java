import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MoveToFront {

    private static final int R = 256;

    public static void encode() {
        List<Character> alphabet = createAlphabet();

        while(!BinaryStdIn.isEmpty()) {
            char current = BinaryStdIn.readChar();
            int alphaPosition = 0;
            Iterator<Character> iterator = alphabet.iterator();

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
            alphabet.add(new Character((char) i));
        }
        return alphabet;
    }
    private static List<Character> createAlphabet2() {
        List<Character> alphabet = new LinkedList<>();
        alphabet.add('A');
        alphabet.add('B');
        alphabet.add('C');
        alphabet.add('D');
        alphabet.add('E');
        alphabet.add('F');

        return alphabet;
    }

    public static void decode() {
    }

    public static void main(String[] args) {
//        if (args.length == 0) {
//            throw new IllegalArgumentException("Usage: input '+' for encoding or '-' for decoding");
//        }
//
//        if ("-".equals(args[0])) {
//            BurrowsWheeler.encode();
//        } else if ("+".equals(args[0])) {
//            BurrowsWheeler.decode();
//        } else {
//            throw new java.lang.IllegalArgumentException("Usage: input '+' for encoding or '-' for decoding");
//        }
//        encode();
    }

}
