package de.viathinksoft.immortal.gen2.math;

import java.math.BigInteger;

public class MathUtils {

	/**
	 * Division with remainder method - based on the original Java<br>
	 * 'divideAndRemainder' method<br>
	 * returns an object of results (of type BigInteger):<br>
	 * -> the division result (q=a/b)<br>
	 * result[1] -> the remainder (r=a-q*b)
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
	 * The Extended Euclidean Algorithm
	 * 
	 * @param a
	 * @param gcd
	 * @return Object of "alpha" (inverse), "beta" and gcd (of type BigInteger).
	 * @see http://tupac.euv-frankfurt-o.de/www/kryptos/demos/Demos.java
	 */

	public static ExtEuclResult extEucl(BigInteger a, BigInteger gcd) {

		// Coefficients
		BigInteger alpha_0 = new BigInteger("1");
		BigInteger alpha_1 = new BigInteger("0");
		BigInteger beta_0 = new BigInteger("0");
		BigInteger beta_1 = new BigInteger("1");
		BigInteger alpha = new BigInteger("0");
		BigInteger beta = new BigInteger("1");

		if (gcd.compareTo(BigInteger.ZERO) == 0) {
			alpha = BigInteger.ONE;
			beta = BigInteger.ZERO;
			gcd = a;
		} else if (a.compareTo(BigInteger.ZERO) != 0) {
			DivisionAndRemainderResult qr = divRem(a, gcd);

			while (qr.getRemainder().compareTo(BigInteger.ZERO) != 0) {
				alpha = alpha_0.subtract(qr.getDivisionResult().multiply(
						alpha_1));
				beta = beta_0.subtract(qr.getDivisionResult().multiply(beta_1));

				alpha_0 = alpha_1;
				beta_0 = beta_1;

				alpha_1 = alpha;
				beta_1 = beta;

				a = gcd;
				gcd = qr.getRemainder();
				qr = divRem(a, gcd);
			}
		}

		return new ExtEuclResult(alpha, beta, gcd);
	}

	/**
	 * Determinates wheter p and q are coprimes or not.
	 * 
	 * @param p
	 * @param q
	 * @return
	 */
	public static boolean isCoprime(BigInteger p, BigInteger q) {
		return p.gcd(q).compareTo(BigInteger.ONE) == 0;
	}

	/**
	 * Solves the simultaneous congruences by means of the CR-Theorem:<br>
	 * M = Mp mod P and M = Mq mod Q<br>
	 * M = (Mq*Yp*P + Mp*Yq*Q) mod N<br>
	 * (Yp, Yq -> Ext.Eucl: Yp*P + Yq*Q = 1)
	 * 
	 * @param Mp
	 * @param P
	 * @param Mq
	 * @param Q
	 * @return
	 * @throws CoPrimeExpectedException
	 * @see http://tupac.euv-frankfurt-o.de/www/kryptos/demos/Rsa.java
	 */

	public static BigInteger chineseRemainder(BigInteger Mp, BigInteger P, BigInteger Mq,
			BigInteger Q) throws CoPrimeExpectedException {

		if (!isCoprime(P, Q)) {
			throw new CoPrimeExpectedException();
		}

		// 1) yiMi = 1 mod mi -> computing Inverses yi (extended
		// Euclides):
		// yp*P = 1 mod Q -> yp (inverse)
		// yq*Q = 1 mod P -> yq (inverse)

		BigInteger yq = extEucl(Q, P).getAlpha();
		BigInteger yp = extEucl(P, Q).getAlpha();

		// 2) collecting 'M = (Mp*yq*Q + Mq*yp*P) mod N':

		BigInteger psum = Mp.multiply(yq).multiply(Q);
		BigInteger qsum = Mq.multiply(yp).multiply(P);
		BigInteger sum = psum.add(qsum);

		// computing 'sum mod m'
		BigInteger N = P.multiply(Q); // common modulus
		BigInteger M = divRem(sum, N).getRemainder();

		// if remainder (a/b) is negative (cause 'a' negative) then:
		if (M.compareTo(BigInteger.ZERO) < 0) {
			M = M.add(N);
		}

		return M;
	}
	
	private MathUtils() {
	}

}
