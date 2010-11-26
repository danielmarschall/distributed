package de.viathinksoft.immortable.gen2;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;

public class ImmortableTest {

	@Test
	public void isImmortableTest() {
		assertFalse(Immortable.isImmortable(new BigInteger("0123")));
		assertTrue(Immortable.isImmortable(new BigInteger("5")));
		assertTrue(Immortable.isImmortable(new BigInteger("25")));
		assertFalse(Immortable.isImmortable(new BigInteger("125")));
		assertTrue(Immortable.isImmortable(new BigInteger("625")));
	}
	
	@Test
	public void findNextImmortableTest() {
		assertEquals("25", Immortable.findNextImmortable("5"));
		assertEquals("625", Immortable.findNextImmortable("25"));
		assertEquals("0625", Immortable.findNextImmortable("625"));
		assertEquals("90625", Immortable.findNextImmortable("0625"));
		assertEquals(null, Immortable.findNextImmortable("2"));
	}
}
