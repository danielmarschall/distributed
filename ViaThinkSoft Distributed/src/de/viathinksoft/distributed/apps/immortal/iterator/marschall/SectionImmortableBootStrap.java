package de.viathinksoft.distributed.apps.immortal.iterator.marschall;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import de.viathinksoft.distributed.apps.immortal.exception.InitialNumberIsOneException;
import de.viathinksoft.distributed.apps.immortal.exception.InitialNumberNotImmortableException;

public class SectionImmortableBootStrap {
	protected static void initializeSectionImmortableNumberFile(String filename, BigInteger initialNumber) throws InitialNumberNotImmortableException, InitialNumberIsOneException, NoSuchAlgorithmException, IOException {
		SectionImmortableNumber rs = new SectionImmortableNumber(initialNumber);
		rs.saveToFile(filename);
	}
	
	public static void initializeSectionImmortableChain5File(String filename) throws NoSuchAlgorithmException, InitialNumberNotImmortableException, InitialNumberIsOneException, IOException {
		initializeSectionImmortableNumberFile(filename, new BigInteger("5"));
	}
	
	public static void initializeSectionImmortableChain6File(String filename) throws NoSuchAlgorithmException, InitialNumberNotImmortableException, InitialNumberIsOneException, IOException {
		initializeSectionImmortableNumberFile(filename, new BigInteger("6"));
	}
	
	private SectionImmortableBootStrap() {
	}
	
}
