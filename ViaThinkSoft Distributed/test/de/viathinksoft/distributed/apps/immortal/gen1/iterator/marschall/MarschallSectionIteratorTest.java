package de.viathinksoft.distributed.apps.immortal.gen1.iterator.marschall;

import static org.junit.Assert.*;

import org.junit.Test;

import de.viathinksoft.distributed.apps.immortal.gen1.exception.InitialNumberIsOneException;
import de.viathinksoft.distributed.apps.immortal.gen1.exception.InitialNumberNotImmortableException;
import de.viathinksoft.distributed.apps.immortal.gen1.iterator.marschall.MarschallSectionIterator;
import de.viathinksoft.distributed.apps.immortal.gen1.iterator.marschall.SectionImmortableNumber;

public class MarschallSectionIteratorTest {
	
	@Test
	public void chain6Test() throws InitialNumberNotImmortableException, InitialNumberIsOneException {
		SectionImmortableNumber r = new SectionImmortableNumber(6);
		MarschallSectionIterator x = new MarschallSectionIterator(r);
		assertEquals(r.toString(), "6");
		for (int i=0; i < 10; i++) {
			r = x.next();
		}
		assertEquals("400,8,1,7,8,7,10,9,3,7,6", r.toString());
		for (int i=0; i < 4; i++) {
			r = x.next();
		}
		assertEquals("7,4,3,7,400,8,1,7,8,7,10,9,3,7,6", r.toString());
	}

	@Test
	public void chain5Test() throws InitialNumberNotImmortableException, InitialNumberIsOneException {
		SectionImmortableNumber r = new SectionImmortableNumber(5);
		MarschallSectionIterator x = new MarschallSectionIterator(r);
		assertEquals(r.toString(), "5");
		for (int i=0; i < 10; i++) {
			r = x.next();
		}
		assertEquals("9,1,8,2,1,2,8,90,6,2,5", r.toString());
		for (int i=0; i < 4; i++) {
			r = x.next();
		}
		assertEquals("6,2,5,9,9,1,8,2,1,2,8,90,6,2,5", r.toString());
	}

}
