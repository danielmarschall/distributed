package de.viathinksoft.distributed.apps.immortal.gen1.iterator.sequential;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

public class ImmortalSequenceListBootStrap {

	public static void initializeEmptySequenceFile(String filename,
			BigInteger beginningNumber) throws NoSuchAlgorithmException,
			IOException {
		ImmortalSequenceList rs = new ImmortalSequenceList();
		rs.add(beginningNumber);
		rs.saveToFile(filename);
	}

	private ImmortalSequenceListBootStrap() {
	}

}
