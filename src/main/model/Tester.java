package model;

public class Tester {


    int coffee(int n) {
        int s = n * n;
        for (int q = 0; q < n; q++) {
            s = s - q;
        }
        for (int q = n; q > 0; q--) {
            s = s - q;
        }
        return s + 2;
    }

    int mocha(int n) {
        int r = 0;
        for (int i = 0; i <= n; i = i + 16) {
            for (int j = 0; j < i; j++) {
                r++;
            }
        }
        return r;
    }

    int fun(int n) {
        int j = 0;
        for (int k = 16; coffee(k) * mocha(k) - k <= n; k += 16) {
            j++;

        }
        System.out.println(j);
        return j;
    }
}