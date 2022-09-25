public class Outcast {
    private final WordNet wordNet;

    /**
     * constructor takes a WordNet object.
     */
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    /**
     * given an array of WordNet nouns, return an outcast.
     */
    public String outcast(String[] nouns) {
        int nounId = 0;
        int maxDist = Integer.MIN_VALUE;
        int n = nouns.length;
        for (int i = 0; i < n; ++i) {
            int dist = 0;
            for (int j = 0; j < n; ++j) {
                if (i == j) {
                    continue;
                }
                dist += wordNet.distance(nouns[i], nouns[j]);
            }
            if (dist > maxDist) {
                maxDist = dist;
                nounId = i;
            }
        }
        return nouns[nounId];
    }

    public static void main(String[] args) {
        // empty
    }
}