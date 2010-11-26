package de.viathinksoft.immortable.gen3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

import de.viathinksoft.immortable.gen2.Immortable;
import de.viathinksoft.immortable.gen2.math.MathUtils2;

// Generation 3 needed as soon as MAX_INTEGER is
// reached or fails.

public class Gen3Test {

	public static void main(String[] args) throws IOException {

		String curIM = "5";

		BigInteger u = new BigInteger("1");
		while (true) {
			u = u.add(BigInteger.ONE);

			curIM = Immortable.findNextImmortable(curIM);

			if (MathUtils2.divRem(u, new BigInteger("100")).getRemainder() == BigInteger.ZERO) {
				String s = new StringBuffer(curIM).reverse().toString();

				String filename = "bak\\gen3_m5_" + u.toString() + ".txt";
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
