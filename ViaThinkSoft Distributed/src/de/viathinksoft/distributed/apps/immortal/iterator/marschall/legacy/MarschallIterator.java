package de.viathinksoft.distributed.apps.immortal.iterator.marschall.legacy;

import java.math.BigInteger;
import java.util.Iterator;

import de.viathinksoft.distributed.apps.immortal.exception.InitialNumberNotImmortableException;
import de.viathinksoft.distributed.apps.immortal.iterator.QuickImmortableCheck;

//TODO: Iterable aufspalten?

public class MarschallIterator implements Iterator<BigInteger>, Iterable<BigInteger> {
	
	private BigInteger position;
	
	public MarschallIterator(BigInteger start) throws InitialNumberNotImmortableException {
		if (!QuickImmortableCheck.check(start)) {
			throw new InitialNumberNotImmortableException();
		}

		this.position = start;
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public BigInteger next() {
		String s = position.toString();
		BigInteger prefixTrial = BigInteger.ZERO;
		BigInteger chk;
		do {
			prefixTrial = prefixTrial.add(BigInteger.ONE);
			chk = new BigInteger(prefixTrial.toString().concat(s));
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
