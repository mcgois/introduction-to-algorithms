import edu.princeton.cs.algs4.*;

import java.util.HashMap;
import java.util.Map;

public final class WordNet {

    private final Map<Integer, String> id2synset = new HashMap<>();
    private final Map<String, Bag<Integer>> noun2ids = new HashMap<String, Bag<Integer>>();
    private SAP sap;

    public WordNet(String synsets, String hypernyms) {
        readSynsets(synsets);
        readHypernyms(hypernyms);
    }

    private void readSynsets(String synsetsFile) {
        if (synsetsFile == null) {
            throw new IllegalArgumentException("synsets is null");
        }

        In in = new In(synsetsFile);
        String line;
        while ((line = in.readLine()) != null) {
            String[] items = line.split(",");
            Integer id = Integer.parseInt(items[0]);
            String[] noums = items[1].split(" ");
            String desc = items[2];

            id2synset.put(id, items[1]);
            for (String noum : noums) {
                noun2ids.putIfAbsent(noum, new Bag<Integer>());
                noun2ids.get(noum).add(id);
            }
        }
        in.close();
    }

    private void readHypernyms(String hypernymsFile) {
        if (hypernymsFile == null) {
            throw new IllegalArgumentException("hypernyms is null");
        }

        In in = new In(hypernymsFile);
        Digraph digraph = new Digraph(id2synset.size());
        String line;
        while((line = in.readLine()) != null) {
            String[] items = line.split(",");
            Integer v = Integer.parseInt(items[0]);
            for (int i = 1; i < items.length; i++) {
                Integer w = Integer.parseInt(items[i]);
                digraph.addEdge(v, w);
            }
        }
        in.close();

        checkCycles(digraph);
        verifyRoot(digraph);

        sap = new SAP(digraph);
    }

    private void verifyRoot(Digraph digraph) {
        int roots = 0;
        for (int i = 0; i < digraph.V(); i++) {
            if (!digraph.adj(i).iterator().hasNext()) {
                roots++;
            }
        }

        if (roots != 1) {
            throw new IllegalArgumentException();
        }
    }

    private void checkCycles(Digraph digraph) {
        DirectedCycle directedCycle = new DirectedCycle(digraph);

        if (directedCycle.hasCycle()) {
            throw new IllegalArgumentException();
        }
    }

    public Iterable<String> nouns() {
        return noun2ids.keySet();
    }

    public boolean isNoun(String word) {
        return noun2ids.containsKey(word);
    }

    public int distance(String nounA, String nounB) {
        verifyNoun(nounA);
        verifyNoun(nounB);

        return sap.length(noun2ids.get(nounA), noun2ids.get(nounB));
    }

    private void verifyNoun(String noun) {
        if (!isNoun(noun)) {
            throw new IllegalArgumentException();
        }
    }

    public String sap(String nounA, String nounB) {
        verifyNoun(nounA);
        verifyNoun(nounB);

        Integer ancestor = sap.ancestor(noun2ids.get(nounA), noun2ids.get(nounB));
        return id2synset.get(ancestor);
    }

    public static void main(String... args) {
        WordNet wordNet = new WordNet(args[0], args[1]);

        while(!StdIn.isEmpty()) {
            String nounA = StdIn.readString();
            String nounB = StdIn.readString();

            if (!wordNet.isNoun(nounA)) {
                StdOut.printf("%s is not a noun!\n");
            }

            if (!wordNet.isNoun(nounB)) {
                StdOut.printf("%s is not a noun!\n");
            }

            int discance = wordNet.distance(nounA, nounB);
            String ancestor = wordNet.sap(nounA, nounB);

            StdOut.printf("distance = %d, ancestor = %s\n", discance, ancestor);
        }
    }

}
