package de.viathinksoft.distributed.apps.immortal.gen1.deepcheck;

import java.math.BigInteger;
import java.util.Iterator;

// TODO: Iterable aufspalten?

public class DeepImmortalCheckIterator implements Iterator<DeepImmortalCheckState>,
		Iterable<DeepImmortalCheckState> {

	private DeepImmortalCheckState position;

	public DeepImmortalCheckIterator(DeepImmortalCheckState start) {
		this.position = start; 
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public DeepImmortalCheckState next() {
		if (!position.isImmortal() && (position.getCount().compareTo(BigInteger.ZERO) > 0)) return position;
		
		BigInteger lastResult = position.getLastResult();
		BigInteger initialNumber = position.getInitialNumber();
		BigInteger counter = position.getCount().add(BigInteger.ONE);

		lastResult = lastResult.multiply(initialNumber);
		boolean isImmortable = lastResult.toString().endsWith(initialNumber.toString());
		
		position = new DeepImmortalCheckState(isImmortable, counter, initialNumber, lastResult);

		return position;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<DeepImmortalCheckState> iterator() {
		return this;
	}

}
