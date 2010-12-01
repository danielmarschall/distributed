package de.viathinksoft.immortal.genX;

public class MainUnit {

	public static void main(String[] args) throws ImmortalException {
		ImmortalNumberSearch x = new ImmortalNumberSearch("data.txt");
		x.calcIterate(-1);
		// x.calcIterate(10);
	}

}
