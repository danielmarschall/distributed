package de.viathinksoft.immortal.bases;

import java.util.Vector;

public class SearchRoots {

	public static boolean isImmortal(int num, int base) {
		String quadrat = "" + PowMod.powmod(num, 2, base);
		return (quadrat.endsWith("" + num));
	}

	private static void checkBase(int b) {
		Vector<String> roots = new Vector<String>();

		for (int i = 0; i < b; i++) {

			if (isImmortal(i, b)) {
				roots.add("" + i);
			}
		}

		System.out.println("Base " + b + ": " + roots);
	}

	public static void main(String[] args) {
		for (int i = 1; i < 255; i++) {
			checkBase(i);
		}
	}

}
