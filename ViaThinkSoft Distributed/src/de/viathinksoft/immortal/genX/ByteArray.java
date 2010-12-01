package de.viathinksoft.immortal.genX;

/**
 * 
 * @author Daniel Marschall
 * 
 */
public class ByteArray {

	private int expansionSize;
	private byte[] a;
	private int top = -1;

	public ByteArray() {
		this(100, 1000);
	}

	public ByteArray(int initialSize, int expansionSize) {
		this.a = new byte[initialSize];
		this.expansionSize = expansionSize;
	}

	private void expand() {
		byte[] b = a;

		a = new byte[b.length + expansionSize];

		for (int i = 0; i < b.length; i++) {
			a[i] = b[i];
		}
	}

	public void add(byte x) {
		top++;
		if (top >= a.length) {
			expand();
		}
		a[top] = x;
	}

	public byte remove() {
		return a[top--];
	}

	public byte get(int i) {
		return a[i];
	}

	public void set(int i, byte x) {
		a[i] = x;
	}

	public int count() {
		return top + 1;
	}

	public void clear() {
		top = -1;
	}

	public boolean isEmpty() {
		return top == -1;
	}

	@Override
	public String toString() {
		StringBuilder x = new StringBuilder();
		for (int i = 0; i <= top; i++) {
			x.append("" + a[i]);
		}
		return x.toString();
	}

}
