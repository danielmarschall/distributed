package de.viathinksoft.immortable.gen4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigInteger;

import de.viathinksoft.immortable.gen2.Immortable;
import de.viathinksoft.immortable.gen2.math.MathUtils2;

// Ist SEHR viel schneller als Generation 3
// Kann die Suche fortsetzen
// Nähert sich an MAX_INT an und man kann nachvollziehen, wann es fehlschlägt
// Allerdings hat man wohl ein Problem mit MAX_INT + 1

public class Gen4Test {
	
	private static String STEPS = "10000";

	public static void main(String[] args) throws Exception {
		BigInteger u;
		
		String filename = "gen4_m5.txt";
		if (new File(filename).exists()) {
			BufferedReader r = new BufferedReader(new FileReader(filename));
			String curIM = r.readLine();
			r.close();
			
			curIM = new StringBuffer(curIM).reverse().toString();
			if (!Immortable.isImmortable(new BigInteger(curIM))) {
				System.out.println("Eingabe nicht immortable");
				System.exit(1);
			}

			u = MathUtils2.length(new BigInteger(curIM));
		} else {
			u = new BigInteger("0");
		}
		
		while (true) {
			u = u.add(new BigInteger(STEPS));
			
			BigInteger curIM = Immortable.M5(u);
			
			String s = new StringBuffer(curIM.toString()).reverse().toString();

			filename = "bak/gen4_m5_" + u.toString() + ".txt";
			BufferedWriter f = new BufferedWriter(new FileWriter(filename));
			f.write(s);
			f.close();

			filename = "gen4_m5.txt";
			f = new BufferedWriter(new FileWriter(filename));
			f.write(s);
			f.close();
		}
	}

}
