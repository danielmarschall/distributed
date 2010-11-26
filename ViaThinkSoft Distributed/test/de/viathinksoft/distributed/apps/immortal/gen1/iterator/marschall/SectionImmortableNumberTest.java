package de.viathinksoft.distributed.apps.immortal.gen1.iterator.marschall;

import static org.junit.Assert.fail;

import org.junit.Test;

import de.viathinksoft.distributed.apps.immortal.gen1.exception.InitialNumberIsOneException;
import de.viathinksoft.distributed.apps.immortal.gen1.exception.InitialNumberNotImmortableException;
import de.viathinksoft.distributed.apps.immortal.gen1.iterator.marschall.SectionImmortableNumber;

public class SectionImmortableNumberTest {
	
	@Test
	public void oneExceptionTest() throws InitialNumberNotImmortableException {
		try {
			new SectionImmortableNumber(1);
			fail();
		} catch (InitialNumberIsOneException e) {
		}
	}

	@Test
	public void mortableExceptionTest() throws InitialNumberIsOneException {
		try {
			new SectionImmortableNumber(12);
			fail();
		} catch (InitialNumberNotImmortableException e) {
		}
	}
	
	@Test
	public void successfulConstructTest() throws InitialNumberNotImmortableException, InitialNumberIsOneException {
		new SectionImmortableNumber(25);
	}

}
