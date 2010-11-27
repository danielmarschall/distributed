package de.viathinksoft.immortable.gen2.math;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;

import de.viathinksoft.immortable.gen2.math.CRTNotSolveableException;
import de.viathinksoft.immortable.gen2.math.MathUtils2;
import de.viathinksoft.immortable.gen2.math.RemainderNotSmallerThanModulusException;

public class MathUtils2Test {
	
	@Test
	public void powTest() {
		BigInteger x = new BigInteger("123");
		
		for (int y=-3; y<0; y++) {
			try {
				MathUtils2.pow(x, new BigInteger(""+y));
				fail();
			} catch (ArithmeticException e) {
			}
		}
		
		for (int y=0; y<=3; y++) {
			assertEquals(
					x.pow(y),
					MathUtils2.pow(x, new BigInteger(""+y))
			);
		}
	}

	/**
	 * @throws CRTNotSolveableException
	 * @throws RemainderNotSmallerThanModulusException
	 */

	@Test
	public void chineseRemainderTest() throws CRTException {
		BigInteger x;
		
		// 45 2 65 3 -> Rest muss kleiner sein als Modul
		try {
			x = MathUtils2.chineseRemainder(new BigInteger("45"),
					new BigInteger("2"), new BigInteger("65"), new BigInteger(
							"3"));
			fail();
		} catch (RemainderNotSmallerThanModulusException e) {
		}

		// 1 8 0 125 -> 625
		x = MathUtils2
				.chineseRemainder(new BigInteger("1"), new BigInteger("8"),
						new BigInteger("0"), new BigInteger("125"));
		assertEquals(new BigInteger("625"), x);

		// 1 2 0 4 -> Keine Lösung
		try {
			x = MathUtils2.chineseRemainder(new BigInteger("1"),
					new BigInteger("2"), new BigInteger("0"), new BigInteger(
							"4"));
			fail();
		} catch (CRTNotSolveableException e) {
		}

		// 1 6 3 8 -> 19
		x = MathUtils2.chineseRemainder(new BigInteger("1"),
				new BigInteger("6"), new BigInteger("3"), new BigInteger("8"));
		assertEquals(new BigInteger("19"), x);

		// -1 7 -3 8 -> 43
		x = MathUtils2.chineseRemainder(new BigInteger("-1"), new BigInteger(
				"7"), new BigInteger("-3"), new BigInteger("8"));
		assertEquals(new BigInteger("43"), x);

		// -1 -7 -3 -8 -> 43
		x = MathUtils2.chineseRemainder(new BigInteger("-1"), new BigInteger(
				"-7"), new BigInteger("-3"), new BigInteger("-8"));
		assertEquals(new BigInteger("43"), x);

		// 1 -7 3 -8 -> 43
		x = MathUtils2.chineseRemainder(new BigInteger("1"), new BigInteger(
				"-7"), new BigInteger("3"), new BigInteger("-8"));
		assertEquals(new BigInteger("43"), x);
	}
}
