package de.viathinksoft.distributed.apps.immortal.iterator;

import java.math.BigInteger;

import de.viathinksoft.distributed.apps.immortal.deepcheck.DeepImmortalCheckIterator;
import de.viathinksoft.distributed.apps.immortal.deepcheck.DeepImmortalCheckState;


public class QuickImmortableCheck {
	
	public static final int iterationCount = 5;
		
	public static boolean check(BigInteger number) {
		DeepImmortalCheckState cs = new DeepImmortalCheckState(false, BigInteger.ZERO, number, number);
		DeepImmortalCheckIterator x = new DeepImmortalCheckIterator(cs);
		for (int i = 0; i < iterationCount; i++) {
			cs = x.next();
		}
		return cs.isImmortal();
	}
	
	private QuickImmortableCheck() {
	}

}
