package de.viathinksoft.immortal.gen2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;



public class ImmortalWriter {

	public static void writeImmortable(ImmortalBase b, BigInteger u,
			String filename, boolean reverse) throws IOException {
		BufferedWriter f = new BufferedWriter(new FileWriter(filename));

		String s = null;

		if (b == ImmortalBase.M5) {
			s = Immortal.M5(u).toString();
		} else if (b == ImmortalBase.M6) {
			s = Immortal.M6(u).toString();
		}

		if (reverse) {
			s = new StringBuffer(s).reverse().toString();
		}

		f.write(s);

		f.close();
	}
	
	private ImmortalWriter() {
	}

}
