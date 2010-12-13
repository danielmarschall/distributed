package de.viathinksoft.marschall.raumplan.formula;

import static org.junit.Assert.*;

import org.junit.Test;

public class FormulaProbeTest {

	@Test
	public void GeneralTests() throws SelfTestException, InvalidValException {
		assertTrue(nearlyEquals(FormulaProbe.b_star(), 0.6823278038280193));
		assertTrue(nearlyEquals(FormulaProbe.g_star(), 0.3819660112501051));
		assertTrue(nearlyEquals(FormulaProbe.w_star(), 0.2838458789785244));

		assertTrue(nearlyEquals(FormulaProbe.w_star(), FormulaProbe.w23(
				FormulaProbe.b_star(), FormulaProbe.g_star())));
		assertTrue(nearlyEquals(FormulaProbe.w_star(), FormulaProbe.w3(
				FormulaProbe.b_star(), FormulaProbe.g_star())));

		assertTrue(nearlyEquals(0.0, FormulaProbe.K2(0.5, 0.5, 0.5)));
		assertFalse(nearlyEquals(0.0, FormulaProbe.K3(0.5, 0.5, 0.5)));

		assertTrue(nearlyEquals(0.0, FormulaProbe.K2(0.0, 0.5, 0.375)));
		assertFalse(nearlyEquals(0.0, FormulaProbe.K3(0.0, 0.5, 0.375)));

		assertTrue(nearlyEquals(0.0, FormulaProbe.K2(FormulaProbe.b_star(),
				FormulaProbe.g_star(), FormulaProbe.w_star())));
		assertTrue(nearlyEquals(0.0, FormulaProbe.K3(FormulaProbe.b_star(),
				FormulaProbe.g_star(), FormulaProbe.w_star())));

		for (double i = 0; i < 0.99; i += 0.01) {
			assertTrue(nearlyEquals(0.0, FormulaProbe.K2(FormulaProbe.b_star(),
					FormulaProbe.g_star(), i)));
			assertFalse(nearlyEquals(0.0, FormulaProbe.K3(
					FormulaProbe.b_star(), FormulaProbe.g_star(), i)));
		}

		// w23test(0.2, 0.7); // TODO
		w23test(FormulaProbe.b_star(), FormulaProbe.g_star());
		w3test(FormulaProbe.b_star(), FormulaProbe.g_star());
		w2test(0.5, 0.5);
		w3test(0.6, 0.6);
	}

	private static void w23test(double b, double g) throws SelfTestException,
			InvalidValException {
		double w = FormulaProbe.w23(b, g);
		assertTrue(nearlyEquals(0.0, FormulaProbe.K2(b, g, w)));
		assertTrue(nearlyEquals(0.0, FormulaProbe.K3(b, g, w)));
	}

	private static void w2test(double b, double g) throws SelfTestException,
			InvalidValException {
		double w = FormulaProbe.w2(b, g);
		assertTrue(nearlyEquals(0.0, FormulaProbe.K2(b, g, w)));
	}

	private static void w3test(double b, double g) throws SelfTestException,
			InvalidValException {
		double w = FormulaProbe.w3(b, g);
		assertTrue(nearlyEquals(0.0, FormulaProbe.K3(b, g, w)));
	}

	private static boolean nearlyEquals(double a, double b) {
		return Math.abs(Math.abs(a) - Math.abs(b)) < 0.0000000000001;
	}

}
