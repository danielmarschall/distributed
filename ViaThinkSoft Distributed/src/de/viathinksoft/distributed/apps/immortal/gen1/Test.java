package de.viathinksoft.distributed.apps.immortal.gen1;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import de.viathinksoft.distributed.apps.immortal.gen1.deepcheck.DeepImmortalCheckBootStrap;
import de.viathinksoft.distributed.apps.immortal.gen1.exception.FileContentsBadException;
import de.viathinksoft.distributed.apps.immortal.gen1.exception.InitialNumberIsOneException;
import de.viathinksoft.distributed.apps.immortal.gen1.exception.InitialNumberNotImmortableException;
import de.viathinksoft.distributed.apps.immortal.gen1.iterator.marschall.SectionImmortableBootStrap;
import de.viathinksoft.distributed.apps.immortal.gen1.iterator.sequential.ImmortalSequenceList;
import de.viathinksoft.distributed.apps.immortal.gen1.iterator.sequential.ImmortalSequenceListBootStrap;

@SuppressWarnings("unused")
public class Test {

	/**
	 * @param args
	 * @throws InitialNumberIsOneException 
	 * @throws InitialNumberNotImmortableException 
	 * @throws FileContentsBadException 
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException 
	 */
	// TODO: Auch mal über Exceptions mit Cause nachdenken (Nested)
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException, FileContentsBadException, InitialNumberNotImmortableException, InitialNumberIsOneException {
//		MarschallSectionIterator b = new MarschallSectionIterator(new BigInteger("5"));
//		for (SectionImmortableNumber i : b) {
//			System.out.println(i);
//		}

//		SequencialIterator c = new SequencialIterator(new BigInteger("5"));
//		for (BigInteger i : c) {
//			System.out.println(i);
//		}
		
//		MarschallIterator b = new MarschallIterator(new BigInteger("5"));
//		for (BigInteger i : b) {
//			System.out.println(i);
//		}
		
		SectionImmortableBootStrap.initializeSectionImmortableChain5File("c:/bermarxaliter.txt");
		ImmortalJob.continueMarschallSectionIteration("c:/bermarxaliter.txt", 1000);

//		DeepImmortalCheckBootStrap.initializeDeepCheckFile("c:/beren.txt", new BigInteger("25"));
//		ImmortalJob.continueDeepImmortableCheck("c:/beren.txt", 2);

		//ImmortalSequenceListBootStrap.initializeEmptySequenceFile("c:/be.txt", new BigInteger("27"));
//		ImmortalJob.continueSequencialIteration("c:/be.txt", 3);
	}

}
