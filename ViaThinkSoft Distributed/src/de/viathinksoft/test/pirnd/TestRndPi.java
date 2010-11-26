package de.viathinksoft.test.pirnd;

public class TestRndPi {
	
	public static final int CNT = 1000000;
	public static final int SEED = 3424;
	
	// TODO: E mit SEED 3424 wiederholt sich immer???
	
	// TODO: fehler vergleichen mit verschiedenen seeds und einmal auch nur die Verteilung von e und pi vergleichen!
	
	public static String percent(int x, int complete) {
		return ""+((float)x/complete*100)+"%";
	}
	
	public static void main(String[] args) {
		int seed = SEED;
		
		DigitIterator di = new PiDigits();
		// DigitIterator di = new EDigits();
		
		TranszedentRnd r = new TranszedentRnd(di, seed);
		
		int count_0 = 0;
		int count_1 = 0;
		int count_2 = 0;
		int count_3 = 0;
		int count_4 = 0;
		int count_5 = 0;
		int count_6 = 0;
		int count_7 = 0;
		int count_8 = 0;
		int count_9 = 0;
		int count_a = 0;
		
		for (int i=0; i<CNT; i++) {
			int d = r.getNextRndDigit();
			
			count_a++;
			if (d == 0) count_0++;
			if (d == 1) count_1++;
			if (d == 2) count_2++;
			if (d == 3) count_3++;
			if (d == 4) count_4++;
			if (d == 5) count_5++;
			if (d == 6) count_6++;
			if (d == 7) count_7++;
			if (d == 8) count_8++;
			if (d == 9) count_9++;
			
			// System.out.println(i + ": " + d);
			
			System.out.print(d);
			if (count_a % 70 == 0) System.out.println("");
		}
		
		System.out.println("");
		System.out.println("");

		System.out.println("Zahl "+di.getName()+" der Länge " + di.length());
		System.out.println("Seed: " + seed);
		System.out.println("Anzahl Zufallszahlen untersucht: " + count_a);
		System.out.println("%-Stat 0: " + percent(count_0, count_a));
		System.out.println("%-Stat 1: " + percent(count_1, count_a));
		System.out.println("%-Stat 2: " + percent(count_2, count_a));
		System.out.println("%-Stat 3: " + percent(count_3, count_a));
		System.out.println("%-Stat 4: " + percent(count_4, count_a));
		System.out.println("%-Stat 5: " + percent(count_5, count_a));
		System.out.println("%-Stat 6: " + percent(count_6, count_a));
		System.out.println("%-Stat 7: " + percent(count_7, count_a));
		System.out.println("%-Stat 8: " + percent(count_8, count_a));
		System.out.println("%-Stat 9: " + percent(count_9, count_a));

		float fehler = 0;
		fehler += Math.abs(0.1 - (float)count_0 / count_a);
		fehler += Math.abs(0.1 - (float)count_1 / count_a);
		fehler += Math.abs(0.1 - (float)count_2 / count_a);
		fehler += Math.abs(0.1 - (float)count_3 / count_a);
		fehler += Math.abs(0.1 - (float)count_4 / count_a);
		fehler += Math.abs(0.1 - (float)count_5 / count_a);
		fehler += Math.abs(0.1 - (float)count_6 / count_a);
		fehler += Math.abs(0.1 - (float)count_7 / count_a);
		fehler += Math.abs(0.1 - (float)count_8 / count_a);
		fehler += Math.abs(0.1 - (float)count_9 / count_a);
		fehler *= 100;
		System.out.println("Fehler: "+fehler+"%");
	}

}
