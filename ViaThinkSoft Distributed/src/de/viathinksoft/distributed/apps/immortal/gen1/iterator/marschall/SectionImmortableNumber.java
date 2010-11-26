package de.viathinksoft.distributed.apps.immortal.gen1.iterator.marschall;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import de.viathinksoft.distributed.apps.immortal.gen1.exception.FileContentsBadException;
import de.viathinksoft.distributed.apps.immortal.gen1.exception.InitialNumberIsOneException;
import de.viathinksoft.distributed.apps.immortal.gen1.exception.InitialNumberNotImmortableException;
import de.viathinksoft.distributed.apps.immortal.gen1.iterator.QuickImmortableCheck;
import de.viathinksoft.utils.security.MD5;

//TODO: Gewisse zahl anwählen

public class SectionImmortableNumber extends ArrayList<BigInteger> {

	private static final long serialVersionUID = 251721127226195138L;

	protected static final String SEPARATOR = ",";

	public SectionImmortableNumber(BigInteger initialElement)
			throws InitialNumberNotImmortableException,
			InitialNumberIsOneException {
		// TODO: initialnumberisone: soll das auch bei "0" passieren? (unendlichschleife)
		if (initialElement.equals(BigInteger.ONE)) {
			throw new InitialNumberIsOneException();
		}
		if (!QuickImmortableCheck.check(initialElement)) {
			throw new InitialNumberNotImmortableException();
		}
		this.add(initialElement);
	}

	// public SectionImmortableNumber(String initialElement)
	// throws InitialNumberNotImmortableException,
	// InitialNumberIsOneException {
	// this(new BigInteger(initialElement));
	// }

	public SectionImmortableNumber(int initialElement)
			throws InitialNumberNotImmortableException,
			InitialNumberIsOneException {
		// this(Integer.toString(initialElement));
		this(new BigInteger(Integer.toString(initialElement)));
	}

	public SectionImmortableNumber(String filename)
			throws NoSuchAlgorithmException, IOException,
			FileContentsBadException {
		loadFromFile(filename);
	}

	public BigInteger getInitialElement() {
		return this.get(0);
	}

	public int getCalculatedAmount() {
		return this.size() - 1;
	}

	@Override
	public String toString() {
		String s = "";
		for (BigInteger m : this) {
			if (s.equals("")) {
				s = m.toString();
			} else {
				s = m.toString().concat(SEPARATOR).concat(s);
			}
		}
		return s;
	}

	public BigInteger toBigInteger() {
		String s = "";
		for (BigInteger m : this) {
			s = m.toString().concat(s);
		}
		return new BigInteger(s);
	}

	// TODO equals, clone

	private final String SIGNATURE = "Immortal Sectioned Marschall Number V001";
	private final String SALT = "AFFAüüü+++MSKGM ignsdg nkoi ajp894jfocwrvw  AFAFAü324§$§$%$&WQ";

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
			for (int i = ary.length - 1; i >= 0; i--) {
				this.add(new BigInteger(ary[i]));
			}
			// for (String s : ary) {
			// this.add(new BigInteger(s));
			// }
	}

}
