import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {

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
        int frist = 3; //BinaryStdIn.readInt();
        String chars = "ARD!RCAAAABB"; // BinaryStdIn.readString();
    }

    public static void main(String[] args) {
        BurrowsWheeler.encode();

        
    }
}
