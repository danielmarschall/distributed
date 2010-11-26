package de.viathinksoft.immortable.gen2;

import java.math.BigInteger;

import de.viathinksoft.immortable.gen2.math.CoPrimeExpectedException;
import de.viathinksoft.immortable.gen2.math.ImmortableBase;
import de.viathinksoft.immortable.gen2.math.MathUtils;
import de.viathinksoft.immortable.gen2.math.MathUtils2;

/**
 * Immortable Number Generator (ING)
 * 
 * @author Daniel Marschall
 */

public class Immortable {

	/**
	 * Calculates M5 or M6
	 * 
	 * @param base
	 * @param u
	 * @return
	 */
	public static BigInteger M(ImmortableBase base, BigInteger u) {
		BigInteger p, q;

		if (base == ImmortableBase.M5) {
			p = BigInteger.ONE;
			q = BigInteger.ZERO;
		} else if (base == ImmortableBase.M6) {
			p = BigInteger.ZERO;
			q = BigInteger.ONE;
		} else {
			p = null;
			q = null;
		}

		BigInteger a = MathUtils2.pow(new BigInteger("2"), u);
		BigInteger b = MathUtils2.pow(new BigInteger("5"), u);

		// To calculate M5:
		// x = 2^u = a mod 1
		// x = 5^u = b mod 0

		// To calculate M6:
		// x = 2^u = a mod 0
		// x = 5^u = b mod 1

		try {
			return MathUtils.chineseRemainder(p, a, q, b);
		} catch (CoPrimeExpectedException e) {
			// Can never happen since 2^u and 5^u are always coprimes.
			return null;
		}
	}

	/**
	 * Gets M5(u)
	 * 
	 * @param u
	 *            Length of number
	 * @return
	 */
	public static BigInteger M5(BigInteger u) {
		return M(ImmortableBase.M5, u);
	}

	/**
	 * Gets M6(u)
	 * 
	 * @param u
	 *            Length of number
	 * @return
	 */
	public static BigInteger M6(BigInteger u) {
		return M(ImmortableBase.M6, u);
	}

	/**
	 * Toggles between M5 and M6 base.
	 * 
	 * @param cur
	 *            Number
	 * @param u
	 *            Length of number (because of possible leading zeros)
	 * @return Number in opposide base.
	 * @throws InvalidLengthException
	 */
	public static BigInteger toggleBase(BigInteger cur, BigInteger u)
			throws InvalidLengthException {
		// Converts M6(u) <-> M5(u)
		// M6(u) = 1 + 10^u - M5(u)
		// M5(u) = 1 + 10^u - M6(u)
		
		if (u.compareTo(MathUtils2.length(cur)) < 0) {
			throw new InvalidLengthException();
		}

		return BigInteger.ONE.add(MathUtils2.pow(BigInteger.TEN, u)).subtract(cur);
	}

	/**
	 * Toggles between M5 and M6 base. The length of the current number is
	 * assumed (and no leading zero).
	 * 
	 * @param cur
	 * @return
	 */
	public static BigInteger toggleBase(BigInteger cur) {
		try {
			return toggleBase(cur, MathUtils2.length(cur));
		} catch (InvalidLengthException e) {
			// Should never happen
			return null;
		}

	}

	private Immortable() {
	}

}
