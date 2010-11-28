package de.viathinksoft.immortable.gen8;

public class Gen8Test {

	private static int[] berechneUnsterblich(int n) {
		int[] a = new int[n];
		int r;
		int u;
		int m;
		if (n <= 0)
			return new int[0];
		a[0] = 5;
		if (n <= 1)
			return new int[0];
		a[1] = 2;
		r = 2;
		u = 1;
		while (u < n - 1) {
			r = (r - a[u]) / 10 + a[u];
			u++;
			for (m = 1; m < u; m++)
				r += a[m] * a[u - m];
			a[u] = r % 10;
		}
		return a;
	}

	public static void main(String[] args) {
		int[] x = berechneUnsterblich(10000);
		for (int i=0; i<x.length; i++) {
			System.out.print(x[i]);
		}
	}

}
