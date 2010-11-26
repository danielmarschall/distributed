package de.viathinksoft.distributed.apps.immortal.iterator.sequential;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;

import de.viathinksoft.distributed.apps.immortal.gen1.iterator.sequential.SequentialIterator;

public class SequentialIteratorTest {

	@Test
	public void findImmortablesTest() {
		BigInteger from = new BigInteger("25");
		SequentialIterator x = new SequentialIterator(from);

		assertEquals(new BigInteger("76"), x.next());
		assertEquals(new BigInteger("376"), x.next());
		assertEquals(new BigInteger("625"), x.next());
	}
}
