package de.viathinksoft.immortable.gen2;

import java.io.IOException;
import java.math.BigInteger;

import de.viathinksoft.immortable.gen2.math.ImmortableBase;

public class M5_MAX_INT {

	public static void main(String[] args) throws IOException {
		String val = "" + Integer.MAX_VALUE;
		ImmortableWriter.writeImmortable(ImmortableBase.M5,
				new BigInteger(val), "m5_" + val + ".txt", true);
	}

}
