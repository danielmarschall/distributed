package de.viathinksoft.immortable.genX;

// TODO: Immort Test + Backups regelmäßig durchführen

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class ImmortIterator {
	
	/*
	 * 	public static boolean isImmortable(BigInteger num) {
		// Alternativ: n²%10^m == n%10^m
		return num.pow(2).toString().endsWith(num.toString());
	}

	 */

	// TODO: Output M5/M6 function...
	// TODO: r als BigInteger?

	private static final String SIGNATURE = "Immortable Interator GenX int DSO 1000";
	private static final int SOFTBREAK = 76;

	private Vector<Integer> a = new Vector<Integer>();
	private int u;
	private int r;
	private int saveInterval = 100000; // TODO: Größer
	private String filename;
	
	// TODO: load+save prüfen

	public ImmortIterator(String filename) throws Exception {
		this.filename = filename;
		load();
	}

	public int getSaveInterval() {
		return this.saveInterval;
	}

	public void setSaveInterval(int saveInterval) {
		this.saveInterval = saveInterval;
	}

	private boolean savePointExists() {
		return new File(filename).exists();
	}

	private void load() throws Exception {
		if (!savePointExists())
			return;

		BufferedReader f = new BufferedReader(new FileReader(filename));

		String s = f.readLine();
		if (!s.equals(SIGNATURE)) {
			throw new Exception("Wrong signature");
		}
		
		f.readLine(); // ""
		f.readLine(); // "(u)"
		
		s = f.readLine();
		u = Integer.parseInt(s);

		f.readLine(); // ""
		f.readLine(); // "(r)"
		
		s = f.readLine();
		r = Integer.parseInt(s); // TODO: more than 1 line
		
		f.readLine(); // ""
		f.readLine(); // "(M5rev)"
		
		a.clear();
		do {
			s = f.readLine();
			if (s == null) break; // eof
			for (int i=0; i<s.length(); i++) {
				a.add(new Integer(s.substring(i, i+1)));
			}
		} while (!s.equals(""));
		
		f.close();
	}

	private void save() throws IOException {
		BufferedWriter f = new BufferedWriter(new FileWriter(filename));

		f.write(SIGNATURE + "\r\n");
		f.write("\r\n");

		f.write("(u)\r\n");
		f.write(u + "\r\n");
		f.write("\r\n");

		f.write("(r)\r\n");
		f.write(r + "\r\n"); // TODO: Softbreak
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

		f.close();
	}

	public void calcIterate(int amount) throws IOException {
		int s, m, k;
		int cnt = 0;

		if (a.size() < 2) {
			a.add(5);
			a.add(2);
			u = 1;
			r = 2;
			cnt += 2;
		}

		while (true) {
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

			if (cnt % saveInterval == 0) {
				save();
			}

			if (++cnt == amount) {
				save();
				break;
			}
		}
	}

}
