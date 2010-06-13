package de.viathinksoft.distributed.apps.immortal.iterator.marschall.legacy;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

import de.viathinksoft.distributed.apps.immortal.exception.InitialNumberNotImmortableException;
import de.viathinksoft.distributed.apps.immortal.iterator.marschall.legacy.MarschallIterator;

public class MarschallIteratorTest {
	
	@Test
	public void chain6Test() throws InitialNumberNotImmortableException {
		BigInteger from = new BigInteger("76");
		MarschallIterator x = new MarschallIterator(from);

		assertEquals(new BigInteger("376"), x.next());
		assertEquals(new BigInteger("9376"), x.next());
		assertEquals(new BigInteger("109376"), x.next());
		assertEquals(new BigInteger("7109376"), x.next());
		assertEquals(new BigInteger("87109376"), x.next());
	}

	@Test
	public void chain5Test() throws InitialNumberNotImmortableException {
		BigInteger from = new BigInteger("25");
		MarschallIterator x = new MarschallIterator(from);

		assertEquals(new BigInteger("625"), x.next());
		assertEquals(new BigInteger("90625"), x.next());
		assertEquals(new BigInteger("890625"), x.next());
		assertEquals(new BigInteger("2890625"), x.next());
		assertEquals(new BigInteger("12890625"), x.next());
	}
}
