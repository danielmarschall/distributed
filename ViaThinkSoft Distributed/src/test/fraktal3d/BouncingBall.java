package test.fraktal3d;
import com.jme.app.SimpleGame;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.shape.Sphere;
import com.jme.scene.state.MaterialState;
import com.jme.util.Timer;

public class BouncingBall extends SimpleGame {

	// Modellvariablen
	private Room room = new Room(50, 50, 50, 1);
	private Vector3f mBallMove = new Vector3f();;
	private Sphere ball;

	// Kräfte
	private Vector3f g_mPerSec = new Vector3f(0, -9.81f, 0);
	private Vector3f bounceLoss_mPerSec = new Vector3f(0.99f, 0.9f, 0.99f);

	// Anfangswert
	private Vector3f v_mPerSec = new Vector3f(100, 0, 50);

	@Override
	protected void simpleInitGame() {
		this.ball = createBall("Ball", 50, 50, 2);

		// Scene einstellen
		// Kamaraentfernung erhöhen
		cam.setLocation(new Vector3f(0, 50, 150));

		// Elemente an rootNode anhängen
		rootNode.attachChild(this.room.getRoomNode());
		rootNode.attachChild(this.ball);
	}

	private Sphere createBall(String str, int zSam, int radSam, float radius) {
		Sphere ball = new Sphere(str, zSam, radSam, radius);
		ball.setLocalTranslation(this.mBallMove);

		MaterialState materialState = display.getRenderer()
				.createMaterialState();
		
		float opacityAmount = 0.3f;
		materialState
				.setAmbient(new ColorRGBA(0.0f, 0.0f, 0.0f, opacityAmount));
		materialState
				.setDiffuse(new ColorRGBA(0.1f, 0.5f, 0.8f, opacityAmount));
		materialState
				.setSpecular(new ColorRGBA(1.0f, 1.0f, 1.0f, opacityAmount));
		materialState.setShininess(128.0f);
		materialState
				.setEmissive(new ColorRGBA(0.0f, 0.0f, 0.0f, opacityAmount));

		materialState.setEmissive(new ColorRGBA(0.5f, 0.0f, 0.0f, 0.1f));

		ball.setRenderState(materialState);
		ball.updateRenderState();

		this.mBallMove.y = room.getBorderPosition(RoomBorderEnum.UP).y
				- ball.getRadius();
		this.mBallMove.x = room.getBorderPosition(RoomBorderEnum.LEFT).x
				+ ball.getRadius();
		this.mBallMove.z = room.getBorderPosition(RoomBorderEnum.LEFT).z
				+ ball.getRadius();

		return ball;
	}

	@Override
	protected void simpleUpdate() {
		// Frames werden an die Systemzeit angepasst und für das realistische
		// Model variiert
		float time = Timer.getTimer().getTimePerFrame() * 4;

		calculateForces(this.mBallMove.x >= room
				.getBorderPosition(RoomBorderEnum.LEFT).x
				+ ball.getRadius()
				&& this.mBallMove.x <= room
						.getBorderPosition(RoomBorderEnum.RIGHT).x
						- ball.getRadius(), this.mBallMove.y >= room
				.getBorderPosition(RoomBorderEnum.DOWN).y
				+ ball.getRadius()
				&& this.mBallMove.y <= room
						.getBorderPosition(RoomBorderEnum.UP).y
						- ball.getRadius(), this.mBallMove.z >= room
				.getBorderPosition(RoomBorderEnum.BACK).z
				+ ball.getRadius()
				&& this.mBallMove.z <= room
						.getBorderPosition(RoomBorderEnum.FRONT).z
						- ball.getRadius(), time);

		this.mBallMove.addLocal(this.v_mPerSec.mult(time));
	}

	private void calculateForces(boolean xCollision, boolean yCollision,
			boolean zCollision, float time) {

		// Kollision mit den Wänden
		if (!yCollision) {
			this.bounceLoss_mPerSec.mult(0.9f);

			this.v_mPerSec.x *= this.bounceLoss_mPerSec.x;
			this.v_mPerSec.y *= -this.bounceLoss_mPerSec.y;
			this.v_mPerSec.z *= this.bounceLoss_mPerSec.z;

			if (this.mBallMove.y < room.getBorderPosition(RoomBorderEnum.DOWN).y
					+ ball.getRadius()) {
				this.mBallMove.y = room.getBorderPosition(RoomBorderEnum.DOWN).y
						+ ball.getRadius();

			} else {
				this.mBallMove.y = room.getBorderPosition(RoomBorderEnum.UP).y
						- ball.getRadius();
			}

		} else if (!xCollision) {
			this.v_mPerSec.x *= -this.bounceLoss_mPerSec.y;
			this.v_mPerSec.y *= this.bounceLoss_mPerSec.x;
			this.v_mPerSec.z *= this.bounceLoss_mPerSec.z;

			if (this.mBallMove.x < room.getBorderPosition(RoomBorderEnum.LEFT).x
					+ ball.getRadius()) {
				this.mBallMove.x = room.getBorderPosition(RoomBorderEnum.LEFT).x
						+ ball.getRadius();

			} else {
				this.mBallMove.x = room.getBorderPosition(RoomBorderEnum.RIGHT).x
						- ball.getRadius();
			}
		} else if (!zCollision) {
			this.v_mPerSec.x *= this.bounceLoss_mPerSec.x;
			this.v_mPerSec.y *= this.bounceLoss_mPerSec.z;
			this.v_mPerSec.z *= -this.bounceLoss_mPerSec.y;

			if (this.mBallMove.z < room.getBorderPosition(RoomBorderEnum.BACK).z
					+ ball.getRadius()) {
				this.mBallMove.z = room.getBorderPosition(RoomBorderEnum.BACK).z
						+ ball.getRadius();

			} else {
				this.mBallMove.z = room.getBorderPosition(RoomBorderEnum.FRONT).z
						- ball.getRadius();
			}
		}

		// Kräfte setzen
		this.v_mPerSec.addLocal(this.g_mPerSec.mult(time));

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
