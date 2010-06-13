package de.viathinksoft.distributed.apps.immortal.iterator.sequential;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import de.viathinksoft.distributed.apps.immortal.exception.FileContentsBadException;
import de.viathinksoft.utils.security.MD5;

public class ImmortalSequenceList extends ArrayList<BigInteger> {

	private static final long serialVersionUID = 3701716850542275075L;

	protected static final String SEPARATOR = ";";

	public ImmortalSequenceList() {
		super();
	}

	public ImmortalSequenceList(String filename)
			throws NoSuchAlgorithmException, IOException,
			FileContentsBadException {
		loadFromFile(filename);
	}

	@Override
	public String toString() {
		String s = "";
		for (BigInteger m : this) {
			if (s.equals("")) {
				s = m.toString();
			} else {
				s = s.concat(SEPARATOR).concat(m.toString());
			}
		}
		return s;
	}

	public BigInteger getLastNumber() {
		return this.get(this.size() - 1);
	}

	public BigInteger getFirstNumber() {
		return this.get(0);
	}

	// TODO equals, clone

	private final String SIGNATURE = "Immortal Number Sequence List V001";
	private final String SALT = "AFasoifjsiugdaskdgnakjsdmoi2jtuijwqoikoafojdnsvhfbqimlkwmfkSFSDFADFA>SFasfAQ";

	public void saveToFile(String filename) throws IOException,
			NoSuchAlgorithmException {
		BufferedWriter x = new BufferedWriter(new FileWriter(filename));

		String s;
		x.write(SIGNATURE);
		x.write("\r\n");

		s = this.toString();
		x.write(s);
		x.write("\r\n");
		x.write(MD5.digest(s, SALT));
		x.write("\r\n");

		x.close();
	}

	public void loadFromFile(String filename) throws IOException,
			NoSuchAlgorithmException, FileContentsBadException {
		BufferedReader r = new BufferedReader(new FileReader(filename));

		String f_sig = r.readLine();
		if ((f_sig == null) || (!f_sig.equals(SIGNATURE))) {
			throw new FileContentsBadException();
		}

		String f_commatext = r.readLine();
		if ((f_commatext == null)
				|| (!MD5.digest(f_commatext, SALT).equals(r.readLine()))) {
			throw new FileContentsBadException();
		}

		r.close();

		this.clear();

		String[] ary = f_commatext.split(SEPARATOR);
		for (String s : ary) {
			this.add(new BigInteger(s));
		}
	}

}
