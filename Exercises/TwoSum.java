import edu.princeton.cs.algs4.*;
import java.util.Arrays;

public class TwoSum {
    public static int binarySearch(int[] a, int number) {
        int left = 0;
        int right = a.length;
        while (right - left > 1) {
            int mid = left + (right - left) / 2;
            if (a[mid] <= number) {
                left = mid;
            }
            else {
                right = mid;
            }
        }
        return a[left] == number ? left : -1;
    }

    public static void main(String[] args) {
        In in = new In("E:\\JavaPJ\\DSA\\algs4-data\\8Kints.txt");
        int[] a = in.readAllInts();
        Arrays.sort(a);
        for (int i = 0; i < a.length; ++i) {
            int pos = binarySearch(a, -a[i]);
            if (pos > i) {
                StdOut.println(a[i] + " " + a[pos]);
            }
        }
    }
}
