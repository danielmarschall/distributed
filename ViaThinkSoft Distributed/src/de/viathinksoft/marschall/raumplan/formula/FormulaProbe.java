package de.viathinksoft.marschall.raumplan.formula;

public class FormulaProbe {

	private static boolean nearlyEqual(double a, double b) {
		return Math.abs(Math.abs(a) - Math.abs(b)) < 0.0000000000001;
	}

	private static double pow(double x, double y) {
		return Math.pow(x, y);
	}

	private static double sqrt(double x) {
		return Math.pow(x, 1.0 / 2.0);
	}

	private static double sqrt3(double x) {
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

	public static double w2(double b, double g) throws InvalidValException {
		if (nearlyEqual(b, b_star())) {
			System.out.println("FATAL: w2(b*) is Lambda!");
			throw new InvalidValException();
		}

		if (!((g <= g_star()) && (b > b_star()) || (g >= g_star())
				&& (b < b_star()))) {
			System.out.println("w ist nicht definiert für b=" + b + "; g=" + g);
			throw new InvalidValException();
		}

		return (double) ((1 - b) * (g + 1) * (pow(g, 2) - 3 * g + 1))
				/ (2 * (1 - g) * (pow(b, 3) + b - 1));
	}

	public static double K2(double b, double g, double w)
			throws SelfTestException {
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
			throw new SelfTestException();
		}

		return res;

	}

	// Seite 2

	public static double X(double b, double g) throws InvalidValException {
		if (nearlyEqual(b, b_star())) {
			System.out.println("FATAL: X(b,g) may not have b*");
			throw new InvalidValException();
		}

		return (g + 1) * (pow(g, 2) - 3 * g + 1) * (1 - b + pow(b, 4)) - 2
				* pow(g, 3) * (pow(b, 3) + b - 1);
	}

	public static double X_star(double b, double g) {
		return (g + 1) * (pow(g, 2) - 3 * g + 1) * (1 - b + pow(b, 4))
				* (pow(b, 3) + b - 1) - 2 * pow(g, 3)
				* pow((pow(b, 3) + b - 1), 2);
	}

	public static double b_star_star() {
		return 0.724492; // TODO
	}

	public static double w23(double b, double g) throws SelfTestException,
			InvalidValException {
		if (nearlyEqual(b, b_star()))
			return w3(b, g);

		boolean dec1 = nearlyEqual(X(b, g), 0.0);
		boolean dec2 = nearlyEqual(w3(b, g), w2(b, g));

		if (dec1 != dec2) {
			System.out.println("FATAL: X(b,g) ist falsch");
			throw new SelfTestException();
		}

		if (!dec1) {
			System.out.println("w23 ist nicht definiert für b=" + b + "; g="
					+ g);
			throw new InvalidValException();
		} else {
			return w3(b, g); // == w2(b,g)
		}
	}

	public static double w3(double b, double g) throws InvalidValException {
		if (nearlyEqual(b, b_star_star())) {
			System.out.println("FATAL: w3(b**) is Lambda!");
			throw new InvalidValException();
		}

		if (b > b_star_star()) {
			System.out.println("FATAL: w3(b B b**) < 0!");
			throw new InvalidValException();
		}

		return (double) (pow(g, 3) * (1 - b)) / ((1 - g) * (1 - b - pow(b, 4)));
	}

	public static double K3(double b, double g, double w)
			throws SelfTestException {
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
			throw new SelfTestException();
		}

		return res;
	}

	public static double w_star() throws SelfTestException, InvalidValException {
		double res = (double) (pow(3 - sqrt(5), 3) * (1 - sqrt3(0.5 + sqrt(31.0 / 108.0)) - sqrt3(0.5 - sqrt(31.0 / 108.0))))
				/ (8 * (1 - (double) (3 - sqrt(5)) / 2) * (1
						- sqrt3(0.5 + sqrt(31.0 / 108.0))
						- sqrt3(0.5 - sqrt(31.0 / 108.0)) - pow(
						sqrt3(0.5 + sqrt(31.0 / 108.0))
								+ sqrt3(0.5 - sqrt(31.0 / 108.0)), 4)));
		double res2 = w3(b_star(), g_star());

		if (!nearlyEqual(res, res2)) {
			System.out.println("Self test for w_star() failed!");
			System.out.println(res);
			System.out.println(res2);
			throw new SelfTestException();
		}

		return res;
	}

}
