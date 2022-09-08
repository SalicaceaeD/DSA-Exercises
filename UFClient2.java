import edu.princeton.cs.algs4.*;

public class UFClient2 {
    public static void main(String[] args) {
        int N = StdIn.readInt();
        UF uf = new UF(N);
        int pos = 0;
        while (!StdIn.isEmpty()) {
            ++pos;
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.find(p) != uf.find(q)) {
                uf.union(p, q);
                --N;
                if (N == 1) {
                    break;
                }
            }
        }
        if (N == 1) StdOut.print(pos);
        else StdOut.print("FAILED");
    }
}