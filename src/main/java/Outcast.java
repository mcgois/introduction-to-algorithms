import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    private WordNet wordNet;

    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    public String outcast(String[] nouns) {
        int[][] distances = new int[nouns.length][nouns.length];

        for (int i = 0; i < nouns.length; i++) {
            for (int j = i + 1; j < nouns.length; j++) {
                distances[i][j] = wordNet.distance(nouns[i], nouns[j]);
            }
        }

        return nouns[findMaximumDistance(distances)];
    }

    private int findMaximumDistance(int[][] distances) {
        int resultIndex = 0;
        int maximumDistance = Integer.MIN_VALUE;
        int sum = 0;

        for (int i = 0; i < distances.length; i++) {
            sum = 0;

            for (int j = 0; j < distances[i].length; j++) {
                if (j < i) {
                    sum += distances[j][i];
                } else {
                    sum += distances[i][j];
                }

                if (sum > maximumDistance) {
                    maximumDistance = sum;
                    resultIndex = i;
                }
            }
        }

        return resultIndex;
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }

}
