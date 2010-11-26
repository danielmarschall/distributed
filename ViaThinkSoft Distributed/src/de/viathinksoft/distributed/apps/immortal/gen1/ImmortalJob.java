package de.viathinksoft.distributed.apps.immortal.gen1;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import de.viathinksoft.distributed.apps.immortal.gen1.deepcheck.DeepImmortalCheckIterator;
import de.viathinksoft.distributed.apps.immortal.gen1.deepcheck.DeepImmortalCheckState;
import de.viathinksoft.distributed.apps.immortal.gen1.exception.FileContentsBadException;
import de.viathinksoft.distributed.apps.immortal.gen1.exception.InitialNumberIsOneException;
import de.viathinksoft.distributed.apps.immortal.gen1.exception.InitialNumberNotImmortableException;
import de.viathinksoft.distributed.apps.immortal.gen1.iterator.marschall.MarschallSectionIterator;
import de.viathinksoft.distributed.apps.immortal.gen1.iterator.marschall.SectionImmortableNumber;
import de.viathinksoft.distributed.apps.immortal.gen1.iterator.sequential.ImmortalSequenceList;
import de.viathinksoft.distributed.apps.immortal.gen1.iterator.sequential.SequentialIterator;

public class ImmortalJob {
	
	public static void continueDeepImmortableCheck(String filename, int numCount) throws IOException, NoSuchAlgorithmException, FileContentsBadException {
		DeepImmortalCheckState rs = new DeepImmortalCheckState(filename);

		System.out.println(rs);
		DeepImmortalCheckIterator x = new DeepImmortalCheckIterator(rs);
		for (int i = 0; i < numCount; i++) {
			rs = x.next();
		}
		System.out.println(rs);
		
		System.out.println("result-immortal-deepMultiplyInitial-"+rs.getInitialNumber()+"-"+rs.getCount()+".txt");
		
		rs.saveToFile(filename);
	}
	
	public static void continueMarschallSectionIteration(String filename, int numCount) throws IOException, NoSuchAlgorithmException, FileContentsBadException, InitialNumberNotImmortableException, InitialNumberIsOneException {
//		System.out.println((new File(filename)).getParent());

		SectionImmortableNumber se = new SectionImmortableNumber(filename);

		MarschallSectionIterator mi = new MarschallSectionIterator(se);
		
		System.out.println(se);
		for (int i = 0; i < numCount; i++) {
			se = mi.next();
		}
		System.out.println(se);
		
		System.out.println("result-immortal-marschallIteration-"+se.getInitialElement()+"-"+se.getCalculatedAmount()+".txt");
		
		se.saveToFile(filename);
	}
	
	public static void continueSequencialIteration(String filename, int numCount) throws NoSuchAlgorithmException, IOException, FileContentsBadException {
		ImmortalSequenceList sl = new ImmortalSequenceList();
		sl.loadFromFile(filename);
		
		System.out.println(sl);
		SequentialIterator x = new SequentialIterator(sl.getLastNumber());
		for (int i = 0; i < numCount; i++) {
			sl.add(x.next());
		}
		System.out.println(sl);
		
		System.out.println("result-immortal-sequencialSearch-"+sl.getFirstNumber()+"-"+sl.getLastNumber()+".txt");

		sl.saveToFile(filename);
	}
	
	private ImmortalJob() {
	}
}
