package de.viathinksoft.immortable.gen5;

/*
 * gen4 h�tte bei dieser stelle
 * 1:02 h gebraucht.
 * 
 * wie lange dauert es hier?
 * 3:16 h
 */

import java.io.IOException;
import java.math.BigInteger;

import de.viathinksoft.immortable.gen2.math.CRTException;
import de.viathinksoft.immortable.gen2.math.CoPrimeExpectedException;

// Generation 5: EXPERIMENTAL
// Versuch, "t"-Erzeugung f�r M6 zu realisieren
// Versuch, Geschwindigkeit drastisch zu steigen und Zeit f�r
// Neu-Berechnungen durch kontinuierliche Rechnung zu sparen.
// Nachteil: Rechenfehler k�nnen nun nicht mehr diagnostiziert werden, da Appended wird.
// Im Moment noch nicht MAX_INTEGER+1 tauglich (da es sowieso fraglich ist, ob String das aush�lt)

// Fehlschlag ... Die Zeit ist zu stark exponentiell und nicht wie erhofft linear

public class Gen5Test {

	private static final BigInteger CONST_2 = new BigInteger("2");
	private static final BigInteger CONST_M2 = new BigInteger("-2");
	private static final BigInteger CONST_5 = new BigInteger("5");
	private static final BigInteger CONST_9 = new BigInteger("9");

	private static final BigInteger[][] lookUpCR = {
			{ BigInteger.valueOf(0), BigInteger.valueOf(5) },
			{ BigInteger.valueOf(6), BigInteger.valueOf(1) },
			{ BigInteger.valueOf(2), BigInteger.valueOf(7) },
			{ BigInteger.valueOf(8), BigInteger.valueOf(3) },
			{ BigInteger.valueOf(4), BigInteger.valueOf(9) } };

	public static BigInteger pow(BigInteger x, BigInteger y) {
		if (y.compareTo(BigInteger.ZERO) < 0)
			throw new IllegalArgumentException();
		// z will successively become x^2, x^4, x^8, x^16, x^32...
		BigInteger z = x;
		BigInteger result = BigInteger.ONE;
		byte[] bytes = y.toByteArray();
		for (int i = bytes.length - 1; i >= 0; i--) {
			byte bits = bytes[i];
			for (int j = 0; j < 8; j++) {
				if ((bits & 1) != 0)
					result = result.multiply(z);
				// short cut out if there are no more bits to handle:
				if ((bits >>= 1) == 0 && i == 0)
					return result;
				z = z.multiply(z);
			}
		}
		return result;
	}

	public static void doIt(int init_u, BigInteger init_r,
			BigInteger init_s, BigInteger init_t, int abbruch)
			throws CoPrimeExpectedException, IOException {

		Datenspeicherung ds = new Datenspeicherung();

		int u = init_u;
		BigInteger r_ = init_r;
		BigInteger s_ = init_s;
		BigInteger t = init_t;

		final boolean want_m5 = false;

		BigInteger p2pow = CONST_2.pow(u - 1); // TODO: leftshift
		BigInteger p5pow = CONST_5.pow(u - 1);

		// test: r halb so klein halten
		boolean wasMod2 = r_.mod(CONST_2).equals(BigInteger.ZERO);
		r_ = r_.divide(CONST_2);

		while (true) {
			BigInteger r = r_;
			BigInteger s = s_;

			ds.add(t, r, s);

			if (want_m5) {
				if (u == 1) {
					t = CONST_5;
				} else {
					t = CONST_9.subtract(t);
				}
			}

			if (u == abbruch) {
				ds.save("ds5_res.txt");
				break;
			}

			int rem = u % 4;

			BigInteger x = null;
			if (rem == 0) {
				x = s.negate();
			} else if (rem == 1) {
				x = s.multiply(CONST_2);
			} else if (rem == 2) {
				x = s;
			} else if (rem == 3) {
				x = s.multiply(CONST_M2);
			} else {
			}

			// WARNUNG: MathUtils2 rechnet falsch/anders!
			// t = MathUtils2.chineseRemainder(x.mod(CONST_5), CONST_5,
			// r.mod(CONST_2), CONST_2);

			// Auch zu lahm:
			int remx = x.mod(CONST_5).intValue();

			// int remx;
			// {
			// String l = x.toString();
			// remx = Integer.parseInt(""+l.charAt(l.length()-1))%5;
			// }

			// int remr = r.mod(CONST_2).intValue();
			int remr = (wasMod2) ? 0 : 1; // test: r halb so klein halten

			// int remr;
			// {
			// String l = r.toString();
			// if (l.endsWith("0") || l.endsWith("2") || l.endsWith("4")
			// || l.endsWith("6") || l.endsWith("8")) {
			// remr = 0;
			// } else {
			// remr = 1;
			// }
			// }

			// int remr =
			// Math.abs(r.toString().endsWith(suffix)().mod(CONST_2).intValue());

			// Zu lahm:
			// t = MathUtils.chineseRemainder(x, CONST_5, r, CONST_2);
			// t = MathUtils.chineseRemainder(BigInteger.valueOf(remx), CONST_5,
			// BigInteger.valueOf(remr), CONST_2);
			t = lookUpCR[remx][remr];

			// p5pow = CONST_5.pow(u);
			// p5pow = pow(CONST_5, BigInteger.valueOf(u));
			p5pow = p5pow.multiply(CONST_5);
			// r_ = t.multiply(p5pow).add(r).divide(CONST_2);

			// test: r halb klein halten
			BigInteger[] dr = t.multiply(p5pow).divideAndRemainder(CONST_2);
			r_ = dr[0].add(dr[1].add(r));
			wasMod2 = r_.mod(CONST_2).equals(BigInteger.ZERO);
			r_ = r_.divide(CONST_2);

			// p2pow = CONST_2.pow(u);
			// p2pow = p2pow.multiply(CONST_2);
			p2pow = p2pow.shiftLeft(1);
			s_ = t.multiply(p2pow).add(s).divide(CONST_5);

			u++;
		}
	}

	public static void main(String[] args) throws CRTException, IOException {
		int a1 = 10000;
		int a2 = 40000;

		// -----------

		long zstVorher = System.currentTimeMillis();
		doIt(1, new BigInteger("3"), new BigInteger("1"), new BigInteger("6"),
				a1);
		long zstNachher = System.currentTimeMillis();
		long delta1 = (zstNachher - zstVorher);

		// -----------

		zstVorher = System.currentTimeMillis();
		doIt(1, new BigInteger("3"), new BigInteger("1"), new BigInteger("6"),
				a2);
		zstNachher = System.currentTimeMillis();
		long delta2 = (zstNachher - zstVorher);

		// -----------

		double c = ((double) delta2 / ((double) a2 / a1 * delta1));

		// if (Immortable.isImmortable(new BigInteger(data.toString()))) {
		// System.out.println("OK");
		// } else {
		// System.out.println("FAIL");
		// }

		System.out.println("Zeit ben�tigt: " + delta1 + " -> " + delta2
				+ " (c=" + c + ")" + " ms");
	}
}
