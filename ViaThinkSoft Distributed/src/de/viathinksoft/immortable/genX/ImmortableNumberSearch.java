package de.viathinksoft.immortable.genX;

// TODO: r als BigInteger

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Vector;

/**
 * Immortable Iterator
 * 
 * @author Daniel Marschall, René Gruber
 * @see http://www.matheboard.de/thread.php?postid=1259938
 */
public class ImmortableNumberSearch {

	private static final String SIGNATURE = "Immortable Number Report File Version 2.0";
	private static final String SIGNATURE_MINOR = "Iterator GenX Java (backup, selftest, int32) r17";
	private static final String END_SIG = "END OF REPORT";
	private static final int SOFTBREAK = 76;

	private Vector<Integer> a = null;
	private String filename;
	private int saveInterval = 10000;
	private int u = -1;
	private int r = -1;
	private String creation_time;
	private String backupDir = "backup";

	public ImmortableNumberSearch(String filename) throws LoadException {
		this.filename = filename;
		load();
	}

	public String getBackupDir() {
		return backupDir;
	}

	public void setBackupDir(String backupDir) {
		this.backupDir = backupDir;
	}

	public int getSaveInterval() {
		return this.saveInterval;
	}

	public Vector<Integer> getA() {
		return a;
	}

	public int getU() {
		return u;
	}

	public int getR() {
		return r;
	}

	public String getFilename() {
		return filename;
	}

	public void setSaveInterval(int saveInterval) {
		this.saveInterval = saveInterval;
	}

	private boolean savePointExists() {
		return new File(filename).exists();
	}

	private void load() throws LoadException {
		if (!savePointExists()) {
			return;
		}

		BufferedReader f;
		try {
			f = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			// Will not happen because of savePointExists().
			return;
		}

		a = null;
		u = -1;
		r = -1;

		try {
			try {
				String s = f.readLine();
				if (!s.equals(SIGNATURE)) {
					throw new LoadException("Wrong signature");
				}

				f.readLine(); // Minor. signature
				f.readLine(); // ""

				f.readLine(); // "(Starting time)"
				creation_time = f.readLine();
				f.readLine(); // ""

				f.readLine(); // "(Save timestamp)"
				f.readLine(); // Timestamp
				f.readLine(); // ""

				f.readLine(); // "(u)"
				s = f.readLine();
				u = Integer.parseInt(s);
				f.readLine(); // ""

				f.readLine(); // "(r)"
				s = f.readLine();
				r = Integer.parseInt(s); // FUTURE: (1) Multi-Line-Support?
				f.readLine(); // ""

				f.readLine(); // "(M5rev)"
				a = new Vector<Integer>();
				do {
					s = f.readLine();
					for (int i = 0; i < s.length(); i++) {
						a.add(new Integer(s.substring(i, i + 1)));
					}
				} while (!s.equals(""));

				if (!integryTest()) {
					throw new LoadException("Corrupt: Not immortable!");
				}

				if (u + 1 != a.size()) {
					throw new LoadException(
							"Corrupt: Formal and actual length mismatch!");
				}

				s = f.readLine();
				if (!s.equals(END_SIG)) {
					throw new LoadException("Corrupt: End-signature mismatch.");
				}
			} finally {
				f.close();
			}
		} catch (IOException e) {
			throw new LoadException(e);
		}
	}

	private void save(boolean integrityTest) throws SaveException {
		if (integrityTest) {
			if (!integryTest()) {
				throw new SaveException("Integrity test failed. Will not save.");
			}
		}

		String timestamp = DateUtils.now("EEE, d MMM yyyy HH:mm:ss Z");
		String timestamp_filename = DateUtils.now("dd-MM-yyyy_HH-mm-ss");

		try {
			BufferedWriter f = new BufferedWriter(new FileWriter(filename));

			try {
				f.write(SIGNATURE + "\r\n");

				f.write(SIGNATURE_MINOR + "\r\n");
				f.write("\r\n");

				f.write("(Starting time)\r\n");
				f.write(creation_time + "\r\n");
				f.write("\r\n");

				f.write("(Save timestamp)\r\n");
				f.write(timestamp + "\r\n");
				f.write("\r\n");

				f.write("(u)\r\n");
				f.write(u + "\r\n");
				f.write("\r\n");

				f.write("(r)\r\n");
				f.write(r + "\r\n"); // FUTURE: (1) Multi-Line-Support?
				f.write("\r\n");

				f.write("(M5rev)\r\n");
				int i = 0;
				for (Integer xa : a) {
					f.write(xa.toString());
					if (++i % SOFTBREAK == 0) {
						f.write("\r\n");
					}
				}
				if (++i % SOFTBREAK != 0) {
					f.write("\r\n");
				}
				f.write("\r\n");

				f.write(END_SIG);
			} finally {
				f.close();
			}
		} catch (IOException e) {
			throw new SaveException("Could not save master file.", e);
		}

		// Make backup

		new File(backupDir).mkdir();
		String bak_filename = backupDir + "/immortal_" + timestamp_filename
				+ "_" + (u + 1) + ".txt";
		try {
			FileUtils.copyFile(new File(filename), new File(bak_filename));
		} catch (IOException e) {
			throw new SaveException("Could not make backup", e);
		}
	}

	public void calcIterate(int amount) throws SaveException {
		int s, m, k;
		int cnt = 0;

		// Wir führen beim ersten Speichern einen weiteren
		// integrity-Test durch. Grund: Wäre bei einer Fortsetzung einer
		// Datei das "r" falsch (der Datenteil aber korrekt), dann würde
		// diese Datei beim Speichern ungültige Daten enthalten (Zahl
		// nicht immortabel).
		boolean firstSave = true;

		if (a == null) {
			creation_time = DateUtils.now("EEE, d MMM yyyy HH:mm:ss Z");
			a = new Vector<Integer>();
			a.add(5);
			a.add(2);
			u = 1;
			r = 2;
			cnt += 2;
		}

		do {
			r = (r - a.elementAt(u)) / 10 + a.elementAt(u);
			s = 0;
			for (m = 1, k = u; m < k; m++, k--) {
				s += a.elementAt(m) * a.elementAt(k);
			}
			r += 2 * s;
			if (m == k) {
				r += a.elementAt(m) * a.elementAt(m);
			}
			u++;
			a.add(r % 10);

			cnt++;
			if (cnt % saveInterval == 0) {
				save(firstSave);
				firstSave = false;
			}
		} while (cnt != amount);

		// Wir brauchen nicht zwei Mal zu speichern
		if (cnt - 1 % saveInterval != 0) {
			save(firstSave);
			firstSave = false;
		}
	}

	public int getDigit(int u) {
		return a.elementAt(u);
	}

	public StringBuilder M5_StringBuilder(int u) {
		StringBuilder s = new StringBuilder();
		for (Integer xa : a) {
			s.append(xa);
		}
		s.reverse();
		return s;
	}

	public String M5_String(int u) {
		return M5_StringBuilder(u).toString();
	}

	public BigInteger M5_BigInteger(int u) {
		return new BigInteger(M5_String(u));
	}

	public StringBuilder M6_StringBuilder(int u) {
		StringBuilder s = new StringBuilder();
		boolean first = true;
		for (Integer xa : a) {
			if (first) {
				s.append(6); // xa = 5
				first = false;
			} else {
				s.append(9 - xa);
			}
		}
		s.reverse();
		return s;
	}

	public String M6_String(int u) {
		return M6_StringBuilder(u).toString();
	}

	public BigInteger M6_BigInteger(int u) {
		return new BigInteger(M6_String(u));
	}

	private static boolean isImmortable(BigInteger num) {
		// Ist das auch OK?
		// return num.pow(2).toString().endsWith(num.toString());

		// n² === n (mod 10^m) <===> n²%10^m == n%10^m
		int m = num.toString().length();
		BigInteger m_pow = BigInteger.TEN.pow(m);
		return num.pow(2).mod(m_pow).equals(num.mod(m_pow));

	}

	public boolean integryTest() {
		// return isImmortable(M5_BigInteger(u));
		return isImmortable(M6_BigInteger(u));
	}
}
