package de.viathinksoft.distributed.apps.immortal.gen1.iterator.marschall;

import java.math.BigInteger;
import java.util.Iterator;

import de.viathinksoft.distributed.apps.immortal.gen1.exception.InitialNumberIsOneException;
import de.viathinksoft.distributed.apps.immortal.gen1.exception.InitialNumberNotImmortableException;
import de.viathinksoft.distributed.apps.immortal.gen1.iterator.QuickImmortableCheck;

public class MarschallSectionIterator implements
		Iterator<SectionImmortableNumber>, Iterable<SectionImmortableNumber> {

	private SectionImmortableNumber position;

	public MarschallSectionIterator(SectionImmortableNumber start)
			throws InitialNumberNotImmortableException {
		if (!QuickImmortableCheck.check(start.toBigInteger())) {
			throw new InitialNumberNotImmortableException();
		}

		this.position = start;
	}

	public MarschallSectionIterator(BigInteger start)
			throws InitialNumberNotImmortableException,
			InitialNumberIsOneException {
		this(new SectionImmortableNumber(start));
	}

	public MarschallSectionIterator(int start)
			throws InitialNumberNotImmortableException,
			InitialNumberIsOneException {
		this(new SectionImmortableNumber(start));
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public SectionImmortableNumber next() {
		String s = position.toBigInteger().toString();
		BigInteger prefixTrial = BigInteger.ZERO;
		BigInteger chk;
		do {
			prefixTrial = prefixTrial.add(BigInteger.ONE);
			chk = new BigInteger(prefixTrial.toString().concat(s));
		} while (!QuickImmortableCheck.check(chk));
		
		SectionImmortableNumber neu = (SectionImmortableNumber) position.clone();
		neu.add(prefixTrial);
		position = neu;
		
		return position;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<SectionImmortableNumber> iterator() {
		return this;
	}
}
