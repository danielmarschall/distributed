package de.viathinksoft.distributed.apps.immortal.gen1.deepcheck;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import de.viathinksoft.distributed.apps.immortal.gen1.exception.FileContentsBadException;
import de.viathinksoft.utils.security.MD5;

public class DeepImmortalCheckState implements Serializable {

	private static final long serialVersionUID = -813320779387593520L;

	private boolean immortal;
	private BigInteger count;
	private BigInteger initialNumber;
	// Warning: There will be no check that continueAt is a valid
	// part of the last result which myint did create.
	private BigInteger lastResult;

	public boolean isImmortal() {
		return immortal;
	}

	public BigInteger getCount() {
		return count;
	}

	public BigInteger getInitialNumber() {
		return initialNumber;
	}

	public BigInteger getLastResult() {
		return lastResult;
	}

	public DeepImmortalCheckState(boolean immortal, BigInteger count, BigInteger initialNumber, BigInteger lastResult) {
		super();
		this.immortal = immortal;
		this.count = count;
		this.initialNumber = initialNumber;
		this.lastResult = lastResult;
	}
	
	public DeepImmortalCheckState(String filename) throws IOException, NoSuchAlgorithmException, FileContentsBadException {
		super();
		loadFromFile(filename);
	}
	
	@Override
	public String toString() {
		return "["+initialNumber+"] " + immortal + " (#" + count + ": " + lastResult + ")";
	}
	
	// TODO equals, clone
	
	private final String SIGNATURE = "Immortal Result Set V001";
	private final String SALT = "omrtjsdflmaeafuinalafm4iosaFFA$§Eeqoqw09kfslmflafnASFmgalm092k928jlamrnln";
	
	public void saveToFile(String filename) throws IOException, NoSuchAlgorithmException {
		BufferedWriter x = new BufferedWriter(new FileWriter(filename));

		String s;
		x.write(SIGNATURE);
		x.write("\r\n");
		
		s = this.initialNumber.toString();
		x.write(s);
		x.write("\r\n");
		x.write(MD5.digest(s, SALT));
		x.write("\r\n");
		
		s = Boolean.toString(this.immortal);
		x.write(s);
		x.write("\r\n");
		x.write(MD5.digest(s, SALT));
		x.write("\r\n");

		s = this.count.toString();
		x.write(s);
		x.write("\r\n");
		x.write(MD5.digest(s, SALT));
		x.write("\r\n");

		s = this.lastResult.toString();
		x.write(s);
		x.write("\r\n");
		x.write(MD5.digest(s, SALT));
		x.write("\r\n");
		
		x.close();
	}
	
	public void loadFromFile(String filename) throws IOException, NoSuchAlgorithmException, FileContentsBadException {
		BufferedReader r = new BufferedReader(new FileReader(filename));
		
		String f_sig = r.readLine();
		if ((f_sig == null) || (!f_sig.equals(SIGNATURE))) {
			throw new FileContentsBadException();
		}
		
		String f_initialnumber = r.readLine();
		if ((f_initialnumber == null) || (!MD5.digest(f_initialnumber, SALT).equals(r.readLine()))) {
			throw new FileContentsBadException();
		}
		
		String f_immortal = r.readLine();
		if ((f_immortal == null) || (!MD5.digest(f_immortal, SALT).equals(r.readLine()))) {
			throw new FileContentsBadException();
		}

		String f_count = r.readLine();
		if ((f_count == null) || (!MD5.digest(f_count, SALT).equals(r.readLine()))) {
			throw new FileContentsBadException();
		}
		
		String f_lastresult = r.readLine();
		if ((f_lastresult == null) || (!MD5.digest(f_lastresult, SALT).equals(r.readLine()))) {
			throw new FileContentsBadException();
		}

		r.close();

		this.initialNumber = new BigInteger(f_initialnumber);
		this.immortal = Boolean.valueOf(f_immortal);
		this.count = new BigInteger(f_count);
		this.lastResult = new BigInteger(f_lastresult);
	}
	
}
