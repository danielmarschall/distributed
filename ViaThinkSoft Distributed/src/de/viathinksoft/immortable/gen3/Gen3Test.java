package de.viathinksoft.immortable.gen3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigInteger;

import de.viathinksoft.immortable.gen2.Immortable;
import de.viathinksoft.immortable.gen2.math.MathUtils2;

// Generation 3 needed as soon as MAX_INTEGER is
// reached or fails.

public class Gen3Test {
	
	private static String STEPS = "1000";

	public static void main(String[] args) throws Exception {
		
		String curIM;
		BigInteger u;

		String filename = "gen3_m5.txt";
		if (new File(filename).exists()) {
			BufferedReader r = new BufferedReader(new FileReader(filename));
			curIM = r.readLine();
			r.close();
			
			curIM = new StringBuffer(curIM).reverse().toString();
			if (!Immortable.isImmortable(new BigInteger(curIM))) {
				System.out.println("Eingabe nicht immortable");
				System.exit(1);
			}

			u = MathUtils2.length(new BigInteger(curIM));
		} else {
			curIM = "5";
			u = new BigInteger("1");
		}
		
		while (true) {
			u = u.add(BigInteger.ONE);

			curIM = Immortable.findNextImmortable(curIM);

			if (MathUtils2.divRem(u, new BigInteger(STEPS)).getRemainder() == BigInteger.ZERO) {
				String s = new StringBuffer(curIM).reverse().toString();

				filename = "bak\\gen3_m5_" + u.toString() + ".txt";
				BufferedWriter f = new BufferedWriter(new FileWriter(filename));
				f.write(s);
				f.close();

				filename = "gen3_m5.txt";
				f = new BufferedWriter(new FileWriter(filename));
				f.write(s);
				f.close();
			}
		}
	}

}
