package de.viathinksoft.marschall.raumplan.formula;

public class FormulaProbe {

	private static final double PRECIOUS = 1000;

	/**
	 * Kollissionsradius für 2D-Raum
	 * 
	 * @param b
	 *            Korridorverkleinerungsfaktor
	 * @param g
	 *            Raumverkleinerungsfaktor
	 * @param w
	 *            Verhältnis des initialen Korridors mit dem Ursprungsraum
	 * @return =0 für Berührung, &lt;0 für Abstand, &gt; für Kollission
	 */
	private static double calc_2d(double b, double g, double w) {
		return (3 - g) / (1 - g) - g + Math.pow(g, 2) - 4 + 2 * w
				* (b / (1 - b) - b - Math.pow(b, 2) - 1);
	}

	/**
	 * Kollissionsradius für 3D-Raum
	 * 
	 * @param b
	 *            Korridorverkleinerungsfaktor
	 * @param g
	 *            Raumverkleinerungsfaktor
	 * @param w
	 *            Verhältnis des initialen Korridors mit dem Ursprungsraum
	 * @return =0 für Berührung, &lt;0 für Abstand, &gt; für Kollission
	 */
	private static double calc_3d(double b, double g, double w) {
		return w * b * (1 / (1 - b) - 1 - b - Math.pow(b, 2)) + 1 / (1 - g) - 1
				- g - Math.pow(g, 2) - w;
	}

	/**
	 * Findet 2D=0 Punkte
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		for (int bi = 0; bi < PRECIOUS; bi++) {
			for (int gi = 0; gi < PRECIOUS; gi++) {
				for (int wi = 0; wi < PRECIOUS; wi++) {
					double b = (double) bi / PRECIOUS;
					double g = (double) gi / PRECIOUS;
					double w = (double) wi / PRECIOUS;
					double r2d = calc_2d(b, g, w);
					double r3d = calc_3d(b, g, w);

					if ((Math.abs(r2d) == 0) /* || (Math.abs(r3d) == 0) */) {
						System.out.println("(b=" + b + ", g=" + g + ", w=" + w
								+ ") = " + r2d + " (2D), " + r3d + " (3D)");
					}
				}
			}
		}

		System.out.println("Beendet");
	}
}
