package de.viathinksoft.test.pirnd;

public class TranszedentRnd {
	
	private int seed;
	private int lastPos = 0;
	private int lastDigit = 0;
	private DigitIterator di;
	
	// Anmerkung: Es kommt zu einem Deadlock bei c=0
	
	public TranszedentRnd(DigitIterator di, int seed) {
		this.di = di;
		this.seed = seed;
	}
	
	// http://www.matheboard.de/thread.php?threadid=435823
	
	/**
	 * @return 0..9
	 */
	public int getNextRndDigit() {
		// System.out.println(lastPos + " + (" + seed + " + " + lastDigit + ") mod " + di.length());
		lastPos = (lastPos + seed + lastDigit) % di.length();
		lastDigit = di.getDigit(lastPos);
		return lastDigit;
	}

}
