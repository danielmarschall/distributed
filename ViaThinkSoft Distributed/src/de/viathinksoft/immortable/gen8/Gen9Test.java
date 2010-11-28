package de.viathinksoft.immortable.gen8;

import java.util.Vector;

public class Gen9Test {

	private static int sav_r;
	private static int sav_u;
	private static Vector<Integer> sav_a;

	private static void calc(Vector<Integer> a, int u, int r, int amount) {
		int s, m, k;
		int cnt = 0;
		while (true) {
			r = (r - a.elementAt(u)) / 10 + a.elementAt(u);
			s = 0;
			for (m = 1, k = u; m < k; m++, k--) {
				s += a.elementAt(m) * a.elementAt(k);
			}
			r += 2 * s;
			if (m == k) {
				r += a.elementAt(m) * a.elementAt(m);
			}
			u++;
			a.add(r % 10);

			// TODO: Hier einen "savepoint" erstellen: (r, u, a[])
			sav_r = r;
			sav_u = u;
			sav_a = a;

			System.out.print(r % 10);

			if (++cnt == amount) break;
		}
	}

	public static void main(String[] args) {
		sav_a = new Vector<Integer>();
		sav_a.add(5);
		sav_a.add(2);
		sav_r = 2;
		sav_u = 1;

		// 60982128199526522937799166014009016980323243247550001183680

		for (int i=0; i<50; i++) {
			calc(sav_a, sav_u, sav_r, 1);
		}
	}

}
