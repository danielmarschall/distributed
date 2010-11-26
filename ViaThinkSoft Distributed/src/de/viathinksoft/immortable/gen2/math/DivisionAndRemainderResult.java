package de.viathinksoft.immortable.gen2.math;

import java.math.BigInteger;

public class DivisionAndRemainderResult {
	
	private BigInteger divisionResult;
	private BigInteger remainder;

	public DivisionAndRemainderResult(BigInteger divisionResult,
			BigInteger remainder) {
		super();
		this.divisionResult = divisionResult;
		this.remainder = remainder;
	}

	public BigInteger getDivisionResult() {
		return divisionResult;
	}

	public BigInteger getRemainder() {
		return remainder;
	}

}
