package de.viathinksoft.immortal.gen2.math;

import java.math.BigInteger;

public class ExtEuclResult {
	
	private BigInteger alpha; // inverse
	private BigInteger beta;
	private BigInteger gcd; // ggT

	public ExtEuclResult(BigInteger alpha, BigInteger beta, BigInteger gcd) {
		super();
		this.alpha = alpha;
		this.beta = beta;
		this.gcd = gcd;
	}
	
	public BigInteger getAlpha() {
		return alpha;
	}
	
	public BigInteger getBeta() {
		return beta;
	}
	
	public BigInteger getGcd() {
		return gcd;
	}

}
