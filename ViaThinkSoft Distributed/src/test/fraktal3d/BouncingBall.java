package test.fraktal3d;
import com.jme.app.SimpleGame;
import com.jme.math.Vector3f;
import com.jme.scene.Node;

public class BouncingBall extends SimpleGame {

	@Override
	protected void simpleInitGame() {
		// Scene einstellen
		// Kamaraentfernung erhöhen
		cam.setLocation(new Vector3f(0, 50, 150));

		// Elemente an rootNode anhängen
		new Raumplan(rootNode, 25, 0, 0, 0, LockDirectoryEnum.LOCK_NOTHING).los(rootNode, 25, 0, 0, 0, LockDirectoryEnum.LOCK_NOTHING);
		
//		rootNode.attachChild(new Raumplan(new Node(), 25, 0, 0, 0, LockDirectoryEnum.LOCK_NOTHING).getRoomNode());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BouncingBall ball = new BouncingBall();
		ball.setConfigShowMode(ConfigShowMode.AlwaysShow);
		ball.start();
	}
}
