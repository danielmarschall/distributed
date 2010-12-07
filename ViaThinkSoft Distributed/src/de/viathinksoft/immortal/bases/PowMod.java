package de.viathinksoft.immortal.bases;
/**
 * Powmod.java
 * 
 * Implementation von a^b mod m durch iteriertes Quadrieren. Zum Testen: Der
 * Aufruf java powmod a b m gibt a^b mod m aus.
 * 
 * @author Thorsten Altenkirch
 * @version Modified by Daniel Marschall
 * 
 */

public class PowMod {

	/**
	 * Ermittelt das nte Bit von l
	 */
	private static boolean bit(long l, int n) {
		return (((l >> n) & 1) != 0);
	}

	/**
	 * Berechnet a^b mod m durch iteriertes Quadrieren.
	 */
	public static long powmod(long a, long m, long n) {
		long y = 1;
		for (int i = 62; i >= 0; i--) {
			y = y * y % n;
			if (bit(m, i)) {
				y = a * y % n;
			}
		}
		return y;
	}
	
	private PowMod() {
	}
}
