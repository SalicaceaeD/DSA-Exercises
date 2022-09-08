import edu.princeton.cs.algs4.*;
import java.util.Arrays;

public class ThreeSum {
    public static void main(String[] args) {
        In in = new In("E:\\JavaPJ\\DSA\\algs4-data\\8Kints.txt");
        int[] a = in.readAllInts();
        Arrays.sort(a);
        for (int i = 0; i < a.length; ++i){
            for (int j = i + 1; j < a.length; ++j) {
                int pos = Arrays.binarySearch(a, -a[i]-a[j]);
                if (pos > j  && pos < a.length && a[i] + a[j] + a[pos] == 0) {
                    StdOut.println(a[i] + " " + a[j] + " " + a[pos]);
                }
            }
        }
    }
}
