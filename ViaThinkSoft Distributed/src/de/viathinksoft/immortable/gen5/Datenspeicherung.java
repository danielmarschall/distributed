package de.viathinksoft.immortable.gen5;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

public class Datenspeicherung {
	
	// private ImmortableBase b;
	private StringBuilder tc = new StringBuilder();
	private int last_u;
	private BigInteger last_r;
	private BigInteger last_s;
	
	public void add(BigInteger t, BigInteger r, BigInteger s) {
		this.last_u++;
		this.last_r = r;
		this.last_s = s;
		this.tc.append(t); // reverse!
	}
	
	public void save(String filename) throws IOException {
		BufferedWriter f = new BufferedWriter(new FileWriter(filename));
		
		String s;

		s = "Gen 5 DS";
		f.write(s);
		
		s = "u = " + last_u;
		f.write(s);
		
		s = "r = " + last_r;
		f.write(s);
		
		s = "s = " + last_s;
		f.write(s);
		
		s = "X = " + tc.toString();
		f.write(s);
		System.out.println(s.subSequence(0, 50)); // debug

		f.close();
	}

}
