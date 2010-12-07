package test.fraktal3d;
import com.jme.app.SimpleGame;
import com.jme.math.Vector3f;

public class BouncingBall extends SimpleGame {

	@Override
	protected void simpleInitGame() {
		cam.setLocation(new Vector3f(0, 50, 150));
		new Raumplan(rootNode, 50, 25, 0, 0, 0, LockDirectoryEnum.LOCK_NOTHING, 1);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BouncingBall ball = new BouncingBall();
		ball.setConfigShowMode(ConfigShowMode.ShowIfNoConfig);
		ball.start();
	}
}
