package de.viathinksoft.immortal.gen2;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;

import de.viathinksoft.immortal.gen2.Immortal;

public class ImmortalTest {

	@Test
	public void isImmortableTest() {
		assertFalse(Immortal.isImmortable(new BigInteger("0123")));
		assertTrue(Immortal.isImmortable(new BigInteger("5")));
		assertTrue(Immortal.isImmortable(new BigInteger("25")));
		assertFalse(Immortal.isImmortable(new BigInteger("125")));
		assertTrue(Immortal.isImmortable(new BigInteger("625")));
	}
	
	@Test
	public void findNextImmortableTest() {
		assertEquals("25", Immortal.findNextImmortable("5"));
		assertEquals("625", Immortal.findNextImmortable("25"));
		assertEquals("0625", Immortal.findNextImmortable("625"));
		assertEquals("90625", Immortal.findNextImmortable("0625"));
		assertEquals(null, Immortal.findNextImmortable("2"));
	}
}
