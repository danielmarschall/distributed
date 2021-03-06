package de.viathinksoft.immortal.genX;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

/**
 * Immortable Iterator
 * 
 * @author Daniel Marschall, Big thanks to the users of MatheBoard.de
 * @see http://www.matheboard.de/archive/435725/thread.html
 */
public class ImmortalNumberSearch {

	private static final String SIGNATURE = "Immortal Number Report File Version 2.02";
	private static final String SIGNATURE_MINOR = "Iterator GenX Java (100k save-interval, load-integrity-check, int32-r, array-object) r38";
	private static final String END_SIG = "END OF REPORT";
	private static final int SOFTBREAK = 76;

	private ByteArray a;
	private String filename;
	private int saveInterval = 100000;
	private int u = -1;
	private int r = -1; // FUTURE: r als BigInteger
	private String creation_time;
	private String backupDir = "backup";

	private static final int INITIAL_SIZE = 1000000;
	private static final int EXPANSION_SIZE = 1000000;

	private boolean do_integrity_test = true;
	private boolean verbose = true;

	public boolean isDo_integrity_test() {
		return do_integrity_test;
	}

	public void setDo_integrity_test(boolean doIntegrityTest) {
		do_integrity_test = doIntegrityTest;
	}

	public boolean isVerbose() {
		return verbose;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	protected void log(String s) {
		if (verbose) {
			String timestamp = DateUtils.now("EEE, d MMM yyyy HH:mm:ss Z");
			// FUTURE: In eine Log-Datei schreiben
			System.out.println(timestamp + " - " + s);
		}
	}

	public ImmortalNumberSearch(String filename) throws LoadException {
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

	public ByteArray getA() {
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
			this.a = new ByteArray(INITIAL_SIZE, EXPANSION_SIZE);
			return;
		}

		BufferedReader f;
		try {
			f = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			// Will not happen because of savePointExists().
			return;
		}

		u = -1;
		r = -1;

		try {
			try {
				String s = f.readLine();
				if (!s.equals(SIGNATURE)) {
					throw new LoadException("Corrupt: Wrong signature");
				}

				f.readLine(); // Minor. signature
				f.readLine(); // ""

				f.readLine(); // "(Starting time)"
				creation_time = f.readLine();
				f.readLine(); // ""

				f.readLine(); // "(Save timestamp)"
				f.readLine(); // Timestamp
				f.readLine(); // ""

				f.readLine(); // "(Digits)"
				s = f.readLine();
				u = Integer.parseInt(s) - 1;
				f.readLine(); // ""

				f.readLine(); // "(r)"
				s = f.readLine();
				r = Integer.parseInt(s); // FUTURE: (1) Multi-Line-Support?
				f.readLine(); // ""

				this.a = new ByteArray(u + 1 + EXPANSION_SIZE, EXPANSION_SIZE);

				f.readLine(); // "(M5 reversed)"
				do {
					s = f.readLine();
					for (int i = 0; i < s.length(); i++) {
						a.add(Byte.parseByte(s.substring(i, i + 1)));
					}
				} while (!s.equals(""));

				if (u + 1 != a.count()) {
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
		if ((integrityTest) && (do_integrity_test)) {
			log("Beginne Selbsttest vor erster Speicherung...");

			if (!integryTest()) {
				throw new SaveException(
						"Integrity test failed. (Loaded file broken?) Will not save.");
			}
		}

		log("Speichere bei " + (u + 1) + " digits");

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

				f.write("(Digits)\r\n");
				f.write((u + 1) + "\r\n");
				f.write("\r\n");

				f.write("(r)\r\n");
				f.write(r + "\r\n"); // FUTURE: (1) Multi-Line-Support?
				f.write("\r\n");

				f.write("(M5 reversed)\r\n");
				int i;
				for (i = 0; i < a.count(); i++) {
					byte xa = a.get(i);
					f.write("" + xa);
					if ((i + 1) % SOFTBREAK == 0) {
						f.write("\r\n");
					}
				}
				if (i % SOFTBREAK != 0) { /* nicht +1, da i++ am Ende */
					f.write("\r\n");
				}
				f.write("\r\n");

				f.write(END_SIG);
				f.write("\r\n");
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

		if ((integrityTest) && (do_integrity_test)) {
			log("Erste Speicherung absolviert. Stabile Phase hat begonnen.");
		}
	}

	public void calcIterate(int amount) throws SaveException {
		log("Beginn der Rechenarbeit...");

		int cnt = 0;

		// Wir f�hren beim ersten Speichern einen weiteren
		// integrity-Test durch. Grund: W�re bei einer Fortsetzung einer
		// Datei das "r" falsch (der Datenteil aber korrekt), dann w�rde
		// diese Datei beim Speichern ung�ltige Daten enthalten (Zahl
		// nicht immortabel).
		boolean firstSave = true;

		if (a.isEmpty()) {
			creation_time = DateUtils.now("EEE, d MMM yyyy HH:mm:ss Z");
			a.add((byte) 5);
			a.add((byte) 2);
			u = 1;
			r = 2;
			cnt += 2;
		}

		int s, m, k;

		do {
			r = (r /* - a.get(u) */) / 10 + a.get(u);
			s = 0;
			for (m = 1, k = u; m < k; m++, k--) {
				s += a.get(m) * a.get(k);
			}
			r += 2 * s;
			if (m == k) {
				r += a.get(m) * a.get(m);
			}
			u++;
			a.add((byte) (r % 10));

			cnt++;
			if (cnt % saveInterval == 0) {
				save(firstSave);
				firstSave = false;
			}
		} while (cnt != amount);

		// Wir brauchen nicht zwei Mal zu speichern
		if (cnt - 1 % saveInterval != 0) {
			log("Letzte Speicherung vor Ende...");
			save(firstSave);
			// firstSave = false;
		}

		log("Ende der Rechenarbeit...");
	}

	public byte getDigitReverse(int u) {
		return a.get(u);
	}

	public StringBuilder M5_StringBuilder(int u) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < a.count(); i++) {
			byte xa = a.get(i);
			s.append("" + xa);
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
		for (int i = 0; i < a.count(); i++) {
			byte xa = a.get(i);
			if (i > 0) {
				s.append("" + (9 - xa));
			} else {
				s.append("" + (11 - xa));
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

	protected static boolean isImmortable(BigInteger num, int m) {
		// Alternativ
		// return num.pow(2).toString().endsWith(num.toString());

		// n� === n (mod 10^m) <===> n�%10^m == n%10^m == n

		BigInteger m_pow = BigInteger.TEN.pow(m);
		
		//return num.pow(2).mod(m_pow).equals(num);
		
		return num.modPow(BigInteger.valueOf(2), m_pow).equals(num);
	}

	protected static boolean isImmortable(BigInteger num) {
		int m = num.toString().length();
		return isImmortable(num, m);
	}

	public boolean integryTest() {
		// return isImmortable(M5_BigInteger(u), a.count());
		return isImmortable(M6_BigInteger(u), a.count());
	}
}
