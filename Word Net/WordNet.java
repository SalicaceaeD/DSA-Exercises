import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WordNet {
    private final List<String> nounsList = new ArrayList<>();
    private final List<String> synsetsList = new ArrayList<>();
    private final HashMap<String, ArrayList<Integer>> nounsMap = new HashMap<>();
    private final SAP sap;
    /**
     * constructor takes the name of the two input files.
     */
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }
        In in = new In(synsets);
        int n = 0;
        while (!in.isEmpty()) {
            String[] s = in.readLine().split(",");

            synsetsList.add(s[1]);
            String[] nounsInSynset = s[1].split(" ");
            for (String st : nounsInSynset) {
                if (!nounsMap.containsKey(st)) {
                    nounsList.add(st);
                }
                ArrayList<Integer> idSet = nounsMap.get(st);
                if (idSet == null) {
                    idSet = new ArrayList<>();
                }
                idSet.add(n);
                nounsMap.put(st, idSet);
            }
            ++n;
        }

        in = new In(hypernyms);
        Digraph digraph = new Digraph(n);
        while (!in.isEmpty()) {
            String[] s = in.readLine().split(",");
            int v = Integer.parseInt(s[0]);
            for (int i = 1; i < s.length; ++i) {
                digraph.addEdge(v, Integer.parseInt(s[i]));
            }
        }
        int count0OutDegree = 0;
        for (int v = 0; v < n; ++v) {
            if (digraph.outdegree(v) == 0) {
                ++count0OutDegree;
            }
        }
        if (count0OutDegree != 1) {
            throw new IllegalArgumentException();
        }
        sap = new SAP(digraph);
    }

    /**
     * returns all WordNet nouns.
     */
    public Iterable<String> nouns() {
        return nounsList;
    }

    /**
     * is the word a WordNet noun?.
     */
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        return nounsMap.containsKey(word);
    }

    /**
     * distance between nounA and nounB.
     */
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        List<Integer> setA = nounsMap.get(nounA);
        List<Integer> setB = nounsMap.get(nounB);
        return sap.length(setA, setB);
    }

    /**
     * a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB.
     * in the shortest ancestral path.
     */
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        List<Integer> setA = nounsMap.get(nounA);
        List<Integer> setB = nounsMap.get(nounB);
        int ancestor = sap.ancestor(setA, setB);
        return synsetsList.get(ancestor);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        // empty
    }
}