package de.viathinksoft.immortable.gen2.math;

import java.math.BigInteger;

public class MathUtils2 {
	
	public static BigInteger length(BigInteger x) {
		// TODO: größer als MAX_INTEGER erlauben!
		return new BigInteger(""+x.toString().length());
	}
	
	public static BigInteger pow(BigInteger base, BigInteger exponent) {
		if (exponent.compareTo(BigInteger.ZERO) < 0) {
			throw new ArithmeticException("Negative exponent");
		}

		BigInteger i = new BigInteger("0");
		BigInteger res = new BigInteger("1");
		
		while (i.compareTo(exponent) != 0) {
			i = i.add(BigInteger.ONE);
			res = res.multiply(base);
		}
		
		return res;
	}

	/**
	 * Division with remainder method - based on the original Java<br>
	 * 'divideAndRemainder' method<br>
	 * returns an object of results (of type BigInteger):<br>
	 * -> the division result (q=a/b)<br>
	 * result[1] -> the remainder (r=a-q*b)<br>
	 * If b==0 then result will be 0. No ArithmeticException!
	 * 
	 * @param a
	 * @param b
	 * @return
	 * @see http://tupac.euv-frankfurt-o.de/www/kryptos/demos/Demos.java
	 **/
	public static DivisionAndRemainderResult divRem(BigInteger a, BigInteger b) {
		// if (b==0) {
		// -> divideAndRemainder() throws ArithmeticException when b==0
		if ((b.compareTo(BigInteger.ZERO)) == 0) {
			return new DivisionAndRemainderResult(BigInteger.ZERO,
					BigInteger.ZERO);
		}

		BigInteger[] x = a.divideAndRemainder(b);
		return new DivisionAndRemainderResult(x[0], x[1]);
	}

	/**
	 * 
	 * @param a
	 * @param b
	 * @return
	 * @see http://www.arndt-bruenner.de/mathe/scripts/chineRestsatz.js
	 */
	private static BigInteger[] extGGT(BigInteger a, BigInteger b) {
		if (b.compareTo(BigInteger.ZERO) == 0) {
			return new BigInteger[] { BigInteger.ONE, BigInteger.ZERO };
		}

		BigInteger rem = divRem(a, b).getRemainder();
		if (rem.compareTo(BigInteger.ZERO) == 0) {
			return new BigInteger[] { BigInteger.ZERO, BigInteger.ONE };
		}

		BigInteger[] c = extGGT(b, rem);
		BigInteger x = c[0];
		BigInteger y = c[1];

		return new BigInteger[] { y, x.subtract(y.multiply(a.divide(b))) };
	}

	/**
	 * Erweiterter euklidscher Algorithmus
	 * 
	 * @param a
	 * @param b
	 * @return Erzeugt Objekt mit mit gcd=ggT(a,b) und gcd=alpha*a+beta*b
	 * @see http://www.arndt-bruenner.de/mathe/scripts/chineRestsatz.js
	 */
	private static ExtEuclResult erwGGT(BigInteger a, BigInteger b) {
		BigInteger aa = new BigInteger("1");
		BigInteger bb = new BigInteger("1");

		if (a.compareTo(BigInteger.ZERO) < 0) {
			aa = new BigInteger("-1");
			a = a.negate();
		}

		if (b.compareTo(BigInteger.ZERO) < 0) {
			bb = new BigInteger("-1");
			b = b.negate();
		}

		BigInteger[] c = extGGT(a, b);
		BigInteger g = a.gcd(b);

		return new ExtEuclResult(c[0].multiply(aa), c[1].multiply(bb), g);
	}

	/**
	 * Allgemeines Lösen zweier Kongruenzen (Chinesischer Restsatz)
	 * 
	 * @param a
	 * @param n
	 * @param b
	 * @param m
	 * @return
	 * @throws CRTNotSolveableException 
	 * @throws RemainderNotSmallerThanModulusException
	 * @see http://de.wikipedia.org/wiki/Chinesischer_Restsatz#Direktes_L.C3.B6sen_von_simultanen_Kongruenzen_ganzer_Zahlen 
	 */
	public static BigInteger chineseRemainder(BigInteger a, BigInteger n,
			BigInteger b, BigInteger m) throws CRTNotSolveableException, RemainderNotSmallerThanModulusException {
		
		// Frage: Ist es notwendig, dass wir divRem() verwenden, das von a%0==0 ausgeht?
		
		if (a.compareTo(BigInteger.ZERO) < 0) {
			a = a.negate();
		}
		
		if (b.compareTo(BigInteger.ZERO) < 0) {
			b = b.negate();
		}
		
		if (n.compareTo(BigInteger.ZERO) < 0) {
			n = n.negate();
		}
		
		if (m.compareTo(BigInteger.ZERO) < 0) {
			m = m.negate();
		}
		
		if ((a.compareTo(n) >= 0) || (b.compareTo(m) >= 0)) {
			throw new RemainderNotSmallerThanModulusException();
		}

		// d = ggT(n,m)
		BigInteger d = n.gcd(m);

		// a === b (mod d) erfüllt?
		if (!divRem(a, d).getRemainder().equals(divRem(b, d).getRemainder())) {
			throw new CRTNotSolveableException();
		}

		// ggT(n,m) = yn + zm
		BigInteger y = erwGGT(n, m).getAlpha();

		// x = a - y*n*((a-b)/d) mod (n*m/d)
		BigInteger N = n.multiply(m).divide(d);
		BigInteger x = divRem(
				a.subtract(y.multiply(n).multiply(a.subtract(b).divide(d))), N)
				.getRemainder();
		x = a.subtract(y.multiply(n).multiply(a.subtract(b).divide(d)));

		while (x.compareTo(BigInteger.ZERO) < 0) {
			x = x.add(N);
		}

		return x;
	}

	private MathUtils2() {
	}

}
