package de.viathinksoft.immortal.gen2;

import java.io.IOException;
import java.math.BigInteger;



public class Gen2Test {

	public static void main(String[] args) throws IOException {
		String val = "" + Integer.MAX_VALUE;
		ImmortalWriter.writeImmortable(ImmortalBase.M5,
				new BigInteger(val), "gen2_m5_" + val + ".txt", true);
	}

}
