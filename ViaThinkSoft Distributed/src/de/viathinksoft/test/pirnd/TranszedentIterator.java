package de.viathinksoft.test.pirnd;

public class TranszedentIterator {

	private int lastPos;
	private DigitIterator di;

	public TranszedentIterator(DigitIterator di) {
		this.di = di;
	}

	/**
	 * @return 0..9
	 */
	public int getNextRndDigit() {
		lastPos++;
		return di.getDigit(lastPos);
	}

}
