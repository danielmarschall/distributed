package de.viathinksoft.distributed.apps.immortal.gen1.deepcheck;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

public class DeepImmortalCheckBootStrap {

	public static void initializeDeepCheckFile(String filename, BigInteger initialNumber) throws NoSuchAlgorithmException, IOException {
		DeepImmortalCheckState rs = new DeepImmortalCheckState(false, BigInteger.ZERO, initialNumber, initialNumber);
		rs.saveToFile(filename);
	}
	
	private DeepImmortalCheckBootStrap() {
	}
}
