package de.viathinksoft.immortal.internal;

import java.math.BigInteger;

public class Endzeitpunkt {

	// Allgemeine Daten zum Test (GenX)
	private static final BigInteger TEST_BEGINN = new BigInteger("1291006160");

	// Der alte Algorithmus (r19)
	// private static final int ABSCHNITT_STEP = 3;
	// private static final BigInteger ABSCHNITT_BEGINN = new BigInteger("789");
	// private static final BigInteger ABFALL_MEDIAN = new BigInteger("199");

	// Der neue Algorithmus (r20)
	private static final int ABSCHNITT_STEP = 29;
	private static final BigInteger ABSCHNITT_BEGINN = new BigInteger("94923");
	private static final BigInteger ABFALL_MEDIAN = new BigInteger("30");

	private static BigInteger f(int u) {
		BigInteger res = ABSCHNITT_BEGINN;
		for (int i = ABSCHNITT_STEP + 1; i <= u; i++) {
			res = res.add(ABFALL_MEDIAN.multiply(BigInteger.valueOf(i)));
		}
		return res;
	}

	private static BigInteger u(int u) {
		return f(u).add(TEST_BEGINN);
	}

	public static void main(String[] args) {
		int max_step = Integer.MAX_VALUE / 100000;

		System.out.println("End time for step: " + max_step);
		System.out.println(u(max_step));
	}

}
