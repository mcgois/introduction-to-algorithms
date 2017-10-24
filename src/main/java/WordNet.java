import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.Map;

public final class WordNet {

    private final Map<Integer, String> idsToSynsets = new HashMap<Integer, String>();
    private final Map<String, Bag<Integer>> nounsToIds = new HashMap<String, Bag<Integer>>();
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
            int id = Integer.parseInt(items[0]);
            String[] noums = items[1].split(" ");
            String desc = items[2];

            idsToSynsets.put(id, items[1]);
            for (String noum : noums) {
                nounsToIds.putIfAbsent(noum, new Bag<Integer>());
                nounsToIds.get(noum).add(id);
            }
        }
        in.close();
    }

    private void readHypernyms(String hypernymsFile) {
        if (hypernymsFile == null) {
            throw new IllegalArgumentException("hypernyms is null");
        }

        In in = new In(hypernymsFile);
        Digraph digraph = new Digraph(idsToSynsets.size());
        String line;
        while ((line = in.readLine()) != null) {
            String[] items = line.split(",");
            int v = Integer.parseInt(items[0]);
            for (int i = 1; i < items.length; i++) {
                int w = Integer.parseInt(items[i]);
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
        return nounsToIds.keySet();
    }

    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        return nounsToIds.containsKey(word);
    }

    public int distance(String nounA, String nounB) {
        verifyNoun(nounA);
        verifyNoun(nounB);

        return sap.length(nounsToIds.get(nounA), nounsToIds.get(nounB));
    }

    private void verifyNoun(String noun) {
        if (!isNoun(noun)) {
            throw new IllegalArgumentException();
        }
    }

    public String sap(String nounA, String nounB) {
        verifyNoun(nounA);
        verifyNoun(nounB);

        int ancestor = sap.ancestor(nounsToIds.get(nounA), nounsToIds.get(nounB));
        return idsToSynsets.get(ancestor);
    }

}
