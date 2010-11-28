package de.viathinksoft.immortable.gen5;

import java.math.BigInteger;

import de.viathinksoft.immortable.gen2.Immortable;

public class Gen7Test {

	private static BigInteger a(String u, int i) {
		if (i < 0)
			return BigInteger.ZERO; // test
		if (i == 0)
			return BigInteger.valueOf(5); // test
		if (i > u.length() - 1)
			return BigInteger.ZERO; // test
		return new BigInteger("" + u.charAt(u.length() - 1 - i));
	}

	private static BigInteger x(String z, int u) {
		BigInteger x = BigInteger.ZERO;
		for (int k = 0; k <= u - 1; k++) {
			for (int m = 0; m <= k; m++) {
				x = x.add(a(z, m).multiply(a(z, k - m)).multiply(
						BigInteger.TEN.pow(k)));
			}
		}
		return x;
	}

	private static BigInteger y(String z, int u) {
		BigInteger y = BigInteger.ZERO;
		for (int m = 1; m <= u - 1; m++) {
			y = y.add(a(z, m).multiply(a(z, u - m)).multiply(
					BigInteger.TEN.pow(u)));
		}
		return y;
	}
	
	private static boolean isImmor2(String z) {
		int u = z.length() + 1;

		BigInteger x = x(z, u);
		BigInteger y = y(z, u);

		BigInteger za = x.add(y).mod(BigInteger.TEN.pow(u + 1));
//System.out.println(za + " <-> " + z + " <-> " + z.substring(z.length()-(u-1)));
		String rr = z.substring(z.length()-(u-1));
		
		return za.toString().endsWith(rr);
	}

	public static void main(String[] args) throws Exception {
		int i = 0;

		StringBuilder z = new StringBuilder("5");

		while (true) {
			i++;
			
			if (!Immortable.isImmortable(new BigInteger(z.toString()))) {
				System.out.println("Nicht immortable: " + z);
			}
			
			StringBuilder rs = new StringBuilder();
			boolean xxx = false;
			for (int p=9; p>=0; p--) {
				//rs = new StringBuilder(Integer.toString(p)).append(z);
				rs = new StringBuilder(Integer.toString(p).concat(z.toString()));
				//System.out.println(rs);
				if (isImmor2(rs.toString())) {
//				if (Immortable.isImmortable(new BigInteger(rs.toString()))) {
					xxx = true;
					break;
				}
			}
			if (!xxx) {
				System.out.println(z);
				throw new Exception("Nix gefunden!");
			}
			z = rs;

			if (i == 1000)
				break;
		}

		System.out.println(z);
	}

}
