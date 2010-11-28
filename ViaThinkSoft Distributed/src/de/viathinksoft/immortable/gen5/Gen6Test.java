package de.viathinksoft.immortable.gen5;

import java.math.BigInteger;

import de.viathinksoft.immortable.gen2.Immortable;

public class Gen6Test {

	private static BigInteger a(String u, int i) {
		if (i < 0)
			return BigInteger.ZERO; // test
		if (i == 0)
			return BigInteger.valueOf(5); // test
		String s = u.toString();
		if (i > s.length() - 1)
			return BigInteger.ZERO; // test
		return new BigInteger("" + s.charAt(s.length() - 1 - i));
	}

	private static String z = "5";

	private static BigInteger x(int u) {
		BigInteger x = BigInteger.ZERO;
		for (int k = 0; k <= u - 1; k++) {
			for (int m = 0; m <= k; m++) {
				x = x.add(a(z, m).multiply(a(z, k - m)).multiply(
						BigInteger.TEN.pow(k)));
			}
		}
		return x;
	}

	private static BigInteger y(int u) {
		BigInteger y = BigInteger.ZERO;
		for (int m = 1; m <= u - 1; m++) {
			y = y.add(a(z, m).multiply(a(z, u - m)).multiply(
					BigInteger.TEN.pow(u)));
		}
		return y;
	}

	public static void main(String[] args) {
		int i = 0;

		BigInteger x = null;
		BigInteger y = null;

		while (true) {
			if (!Immortable.isImmortable(new BigInteger(z))) {
				System.out.println("FEHLER: " + z);
			}

			i++;
			if (i == 50)
				break;

			int u = z.toString().length() + 1;

			// BigInteger x = x(u);
			// BigInteger y = y(u);

			if (x == null) {
				x = x(u);
			} else {
				int k = u - 1;
				for (int m = 0; m <= k; m++) {
					x = x.add(a(z, m).multiply(a(z, k - m)).multiply(
							BigInteger.TEN.pow(k)));
				}
			}

			if (y == null) {
				y = y(u);
			} else {
				int m = u - 1;
				y = y.add(a(z, m).multiply(a(z, u - m)).multiply(
						BigInteger.TEN.pow(u)));
			}

			BigInteger za = x.add(y).mod(BigInteger.TEN.pow(u + 1));
			// z = za;
			// z = new BigInteger(a(za, u-1) + z.toString());
			String m = "0";
			// System.out.println("- "+za);
			while (m.charAt(0) == '0') {
				int p = za.toString().length() - u;
				if (p < 0) {
					m = "00" + za;
					break;
				}
				m = za.toString().substring(p);
				u++;
			}
			z = m;

			// System.out.println(z);
		}

		System.out.println(z);
	}

}
