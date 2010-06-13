package de.viathinksoft.distributed.apps.immortal.iterator.sequential;

import java.math.BigInteger;
import java.util.Iterator;

import de.viathinksoft.distributed.apps.immortal.iterator.QuickImmortableCheck;

//TODO: Iterable aufspalten?

public class SequentialIterator implements Iterator<BigInteger>, Iterable<BigInteger> {
	
	private BigInteger position = BigInteger.ZERO;
	
	public SequentialIterator(BigInteger start) {
		this.position = start; 
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public BigInteger next() {
		BigInteger chk = position;
		
		do {
			chk = chk.add(BigInteger.ONE);
		} while (!QuickImmortableCheck.check(chk));
		position = chk;

		return position;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<BigInteger> iterator() {
		return this;
	}
}
