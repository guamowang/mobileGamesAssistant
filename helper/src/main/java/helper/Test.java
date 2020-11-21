package helper;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        int[] b = {-1, 0, 2, 9, 7, 8, 2, 5, 2};
        int[] c = new int[12];
        int cIndex = 0;
        for (int i = 0; i < b.length; i++) {
            if (b[i] == 2) {
                c[cIndex++] = 100;
            }
            c[cIndex++] = b[i];
        }
        System.out.println(Arrays.toString(c));
    }
}
