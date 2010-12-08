package de.viathinksoft.marschall.raumplan.fraktal3d;
import com.jme.app.SimpleGame;
import com.jme.math.Vector3f;

public class RaumplanFraktal3D extends SimpleGame {

	@Override
	protected void simpleInitGame() {
		cam.setLocation(new Vector3f(0, 50, 150));
		new Kubus(rootNode, 50, 25, 0, 0, 0, LockDirectoryEnum.LOCK_NOTHING, 1);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RaumplanFraktal3D ball = new RaumplanFraktal3D();
		ball.setConfigShowMode(ConfigShowMode.ShowIfNoConfig);
		ball.start();
	}
}
