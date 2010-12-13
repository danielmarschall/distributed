package de.viathinksoft.marschall.raumplan.formula;

public class FormulaProbe {

	protected static double round(double value, int decimalPlace) {
		if (value < 0) {
			// positive value only.
			return -round(-value, decimalPlace);
		}

		double power_of_ten = 1;
		// floating point arithmetic can be very tricky.
		// that's why I introduce a "fudge factor"
		double fudge_factor = 0.05;
		while (decimalPlace-- > 0) {
			power_of_ten *= 10.0d;
			fudge_factor /= 10.0d;
		}
		return Math.round((value + fudge_factor) * power_of_ten) / power_of_ten;
	}

	protected static String roundu(double value) {
		if (Math.abs(value) < 0.000000000000001) {
			// return "<0.000000000000001";
			return "0";
		}
		return "" + round(value, 17);
	}

	protected static boolean nearlyEqual(double a, double b) {
		return Math.abs(Math.abs(a) - Math.abs(b)) < 0.0000000000001;
	}

	protected static double pow(double x, double y) {
		return Math.pow(x, y);
	}

	protected static double sqrt(double x) {
		return Math.pow(x, 1.0 / 2.0);
	}

	protected static double sqrt3(double x) {
		if (x >= 0)
			return Math.pow(x, 1.0 / 3.0);
		else
			return -Math.pow(-x, 1.0 / 3.0);
	}

	// Seite 1

	public static double g_star() {
		return (double) (3 - sqrt(5)) / 2;
	}

	public static double b_star() {
		return sqrt3(0.5 + sqrt(31.0 / 108.0))
				+ sqrt3(0.5 - sqrt(31.0 / 108.0));
	}

	public static double w2(double b, double g) {
		if (nearlyEqual(b, b_star())) {
			System.out.println("FATAL: w2(b*) is Lambda!");
			return Double.NaN;
		}

		if (!((g <= g_star()) && (b > b_star()) || (g >= g_star())
				&& (b < b_star()))) {
			System.out.println("w ist nicht definiert für b=" + b + "; g=" + g);
			return Double.NaN;
		}

		return (double) ((1 - b) * (g + 1) * (pow(g, 2) - 3 * g + 1))
				/ (2 * (1 - g) * (pow(b, 3) + b - 1));
	}

	public static double K2(double b, double g, double w) {
		// Vor Umstellung
		double res = (double) (3 - g) / (1 - g) - g + pow(g, 2) - 4 + 2 * w
				* (b / (1 - b) - b - pow(b, 2) - 1);

		// Nach Umstellen
		double res2 = (double) (-(1 - b) * (g + 1) * (pow(g, 2) - 3 * g + 1) + 2
				* w * (1 - g) * (pow(b, 3) + b - 1))
				/ ((1 - b) * (1 - g));

		if (!nearlyEqual(res, res2)) {
			System.out.println("Fatal in K2");
			System.out.println(res);
			System.out.println(res2);
			System.exit(1);
		}

		return res;

	}

	// Seite 2

	public static double X(double b, double g) {
		if (nearlyEqual(b, b_star())) {
			System.out.println("FATAL: X(b,g) may not have b*");
			return Double.NaN;
		}

		return (g + 1) * (pow(g, 2) - 3 * g + 1) * (1 - b + pow(b, 4)) - 2
				* pow(g, 3) * (pow(b, 3) + b - 1);
	}

	public static double X_star(double b, double g) {
		return (g + 1) * (pow(g, 2) - 3 * g + 1) * (1 - b + pow(b, 4))
				* (pow(b, 3) + b - 1) - 2 * pow(g, 3)
				* pow((pow(b, 3) + b - 1), 2);
	}

	public static double w23(double b, double g) {
		if (nearlyEqual(b, b_star()))
			return w3(b, g);

		boolean dec1 = nearlyEqual(X(b, g), 0.0);
		boolean dec2 = nearlyEqual(w3(b, g), w2(b, g));

		if (dec1 != dec2) {
			System.out.println("FATAL: X(b,g) ist falsch");
			System.exit(1);
			return Double.NaN;
		}

		if (!dec1) {
			System.out.println("w23 ist nicht definiert für b=" + b + "; g="
					+ g);
			return Double.NaN;
		} else {
			return w3(b, g); // == w2(b,g)
		}
	}

	public static double w3(double b, double g) {
		return (double) (pow(g, 3) * (1 - b)) / ((1 - g) * (1 - b + pow(b, 4)));
	}

	public static double K3(double b, double g, double w) {
		// Vor Umstellung
		double res = (1.0 / (1 - g)) - g - pow(g, 2) - 1 - w + b * w
				* ((1.0 / (1 - b)) - b - pow(b, 2) - 1);

		// Nach Umstellen
		double res2 = (double) ((1 - b) * (pow(g, 3)) - w * (1 - b) * (1 - g) + pow(
				b, 4)
				* w * (1 - g))
				/ ((1 - b) * (1 - g));

		if (!nearlyEqual(res, res2)) {
			System.out.println("Fatal in K3");
			System.out.println(res);
			System.out.println(res2);
			System.exit(1);
		}

		return res;
	}

	public static double w_star() {
		double res = (double) (pow(3 - sqrt(5), 3) * (1 - sqrt3(0.5 + sqrt(31.0 / 108.0)) - sqrt3(0.5 - sqrt(31.0 / 108.0))))
				/ (8 * (1 - (double) (3 - sqrt(5)) / 2) * (1
						- sqrt3(0.5 + sqrt(31.0 / 108.0))
						- sqrt3(0.5 - sqrt(31.0 / 108.0)) + pow(
						sqrt3(0.5 + sqrt(31.0 / 108.0))
								+ sqrt3(0.5 - sqrt(31.0 / 108.0)), 4)));

		if (!nearlyEqual(res, w3(b_star(), g_star()))) {
			System.out.println("Self test for w_star() failed!");
			System.exit(1);
			return Double.NaN;
		}

		return res;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// vereinfachte K2 prüfen
		// vereinfachte K3 prüfen (???)
		// Die Formel w2 prüfen
		// Die Formel w3 prüfen
		// Die Formeln w2,3 numerisch prüfen: Ist 2D=3D=0?
		// für b=b*
		// für b!=b*
		// b* g* lambda prüfen: Ist 2D=3D=0
		// b* g* [irgendwas] prüfen: ist 2D=0 und 3D!=0?

		System.out.println("b* = " + roundu(b_star()));
		System.out.println("g* = " + roundu(g_star()));
		System.out.println("w* = " + roundu(w_star()));
		System.out.println("w23(b*, g*) = " + roundu(w23(b_star(), g_star())));
		System.out.println("w3(b*, g*) = " + roundu(w3(b_star(), g_star())));
		System.out.println("K2(.5 .5 .5) = " + roundu(K2(0.5, 0.5, 0.5)));
		System.out.println("K3(.5 .5 .5) = " + roundu(K3(0.5, 0.5, 0.5)));
		System.out.println("K2(0 .5 .375) = " + roundu(K2(0.0, 0.5, 0.375)));
		System.out.println("K3(0 .5 .375) = " + roundu(K3(0.0, 0.5, 0.375)));
		System.out.println("K2(b*, g*, 0.0) = "
				+ roundu(K2(b_star(), g_star(), 0.0)));
		System.out.println("K2(b*, g*, 0.1) = "
				+ roundu(K2(b_star(), g_star(), 0.1)));
		System.out.println("K2(b*, g*, 0.3) = "
				+ roundu(K2(b_star(), g_star(), 0.3)));
		System.out.println("K2(b*, g*, 0.5) = "
				+ roundu(K2(b_star(), g_star(), 0.5)));
		System.out.println("K2(b*, g*, 0.7) = "
				+ roundu(K2(b_star(), g_star(), 0.7)));
		System.out.println("K2(b*, g*, 0.99) = "
				+ roundu(K2(b_star(), g_star(), 0.99)));
		System.out.println("K2(b*, g*, w*) = "
				+ roundu(K2(b_star(), g_star(), w_star())));
		System.out.println("K3(b*, g*, w*) = "
				+ roundu(K3(b_star(), g_star(), w_star())));

		// w23test(0.2, 0.7);
		w23test(b_star(), g_star());
	}

	protected static void w23test(double b, double g) {
		double w = w23(b, g);
		System.out.println("w23(" + b + " " + g + ") = " + roundu(w));
		System.out.println("K2(" + b + " " + g + " " + w + ") = "
				+ roundu(K2(b, g, w)));
		System.out.println("K3(" + b + " " + g + " " + w + ") = "
				+ roundu(K3(b, g, w)));
	}

}
