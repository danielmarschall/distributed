package de.viathinksoft.immortable.genX;

public class MainUnit {

	public static void main(String[] args) throws ImmortableException {
		ImmortableNumberSearch x = new ImmortableNumberSearch("data.txt");
		x.calcIterate(-1);
		// x.calcIterate(10);
	}

}
