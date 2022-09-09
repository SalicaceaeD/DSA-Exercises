import edu.princeton.cs.algs4.*;
import java.util.Arrays;

public class ThreeSum {
    public static void main(String[] args) {
        In in = new In("E:\\JavaPJ\\DSA\\algs4-data\\8ints.txt");
        int[] a = in.readAllInts();
        Arrays.sort(a);
        int N = a.length;
        for (int i = 0; i < N; ++i){
            int run = N-1;
            for (int j = i + 1; j < N; ++j) {
                while (run > j && a[i] + a[j] + a[run] > 0) {
                    --run;
                }
                if (run > j && a[i] + a[j] + a[run] == 0) {
                    StdOut.println(a[i] + " " + a[j] + " " + a[run]);
                }
            }
        }
    }
}
