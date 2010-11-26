package de.viathinksoft.immortable.gen2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

import de.viathinksoft.immortable.gen2.math.ImmortableBase;

public class ImmortableWriter {

	public static void writeImmortable(ImmortableBase b, BigInteger u,
			String filename, boolean reverse) throws IOException {
		BufferedWriter f = new BufferedWriter(new FileWriter(filename));

		String s = null;

		if (b == ImmortableBase.M5) {
			s = Immortable.M5(u).toString();
		} else if (b == ImmortableBase.M6) {
			s = Immortable.M6(u).toString();
		}

		if (reverse) {
			s = new StringBuffer(s).reverse().toString();
		}

		f.write(s);

		f.close();
	}
	
	private ImmortableWriter() {
	}

}
