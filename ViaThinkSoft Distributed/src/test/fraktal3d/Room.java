package test.fraktal3d;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.shape.Box;
import com.jme.scene.state.BlendState;
import com.jme.scene.state.MaterialState;
import com.jme.system.DisplaySystem;

public class Room {
	private Box bottom;
	private Box top;
	private Box left;
	private Box right;
	private Box front;
	private Box back;

	private float thickness;

	public Room(float length, float width, float depth, float thickness) {
		this.thickness = thickness;

		this.bottom = new Box("Bottom", new Vector3f(), width + thickness,
				thickness, depth);

		this.top = new Box("Top", new Vector3f(), width + thickness, thickness,
				depth);
		this.top.setLocalTranslation(new Vector3f(0, length * 2, 0));

		this.left = new Box("Left Border", new Vector3f(), thickness, length,
				depth);
		this.left.setLocalTranslation(new Vector3f(-(width), length, 0));

		this.right = new Box("Right Border", new Vector3f(), thickness, length,
				depth);
		this.right.setLocalTranslation(new Vector3f(width, length, 0));

		this.back = new Box("Back Border", new Vector3f(), width, length,
				thickness);
		this.back.setLocalTranslation(new Vector3f(0, length, -depth));

		this.front = new Box("Front Border", new Vector3f(), width, length,
				thickness);
		this.front.setLocalTranslation(new Vector3f(0, length, depth));
	}

	public Node getRoomNode() {
		Node roomNode = new Node();
		
		float opacityAmount = 0.1f;
		DisplaySystem display = DisplaySystem.getDisplaySystem();
		
		MaterialState materialState = display.getRenderer()
		.createMaterialState();
		
        // the sphere material taht will be modified to make the sphere
        // look opaque then transparent then opaque and so on
        materialState = display.getRenderer().createMaterialState();
        materialState.setAmbient(new ColorRGBA(0.0f, 0.0f, 0.0f, opacityAmount));
        materialState.setDiffuse(new ColorRGBA(0.1f, 0.5f, 0.8f, opacityAmount));
        materialState.setSpecular(new ColorRGBA(1.0f, 1.0f, 1.0f, opacityAmount));
        materialState.setShininess(128.0f);
        materialState.setEmissive(new ColorRGBA(0.0f, 0.0f, 0.0f, opacityAmount));
        materialState.setEnabled(true);
 
        // IMPORTANT: this is used to handle the internal sphere faces when
        // setting them to transparent, try commenting this line to see what
        // happens
        materialState.setMaterialFace(MaterialState.MaterialFace.FrontAndBack);
 
        back.setRenderState(materialState);
        back.updateRenderState();
 
        roomNode.attachChild(back);
 
        // to handle transparency: a BlendState
        // an other tutorial will be made to deal with the possibilities of this
        // RenderState
        final BlendState alphaState = DisplaySystem.getDisplaySystem().getRenderer().createBlendState();
        alphaState.setBlendEnabled(true);
        alphaState.setSourceFunction(BlendState.SourceFunction.SourceAlpha);
        alphaState.setDestinationFunction(BlendState.DestinationFunction.OneMinusSourceAlpha);
        alphaState.setTestEnabled(true);
        alphaState.setTestFunction(BlendState.TestFunction.GreaterThan);
        alphaState.setEnabled(true);
 
        back.setRenderState(alphaState);
        back.updateRenderState();
		
		
		
		roomNode.attachChild(bottom);
		roomNode.attachChild(top);
		roomNode.attachChild(right);
		roomNode.attachChild(left);
		roomNode.attachChild(back);

		return roomNode;
	}

	public Vector3f getBorderPosition(RoomBorderEnum border) {
		Vector3f vec = new Vector3f();

		if (border == RoomBorderEnum.UP) {
			vec = new Vector3f(this.top.getLocalTranslation());
			vec.y -= this.thickness;

		} else if (border == RoomBorderEnum.DOWN) {
			vec = new Vector3f(this.bottom.getLocalTranslation());
			vec.y += this.thickness;
		}

		else if (border == RoomBorderEnum.LEFT) {
			vec = new Vector3f(this.left.getLocalTranslation());
			vec.x += this.thickness;
		}

		else if (border == RoomBorderEnum.RIGHT) {
			vec = new Vector3f(this.right.getLocalTranslation());
			vec.x -= this.thickness;
		}

		else if (border == RoomBorderEnum.FRONT) {
			vec = new Vector3f(this.front.getLocalTranslation());
			vec.z -= this.thickness;
		}

		else if (border == RoomBorderEnum.BACK) {
			vec = new Vector3f(this.back.getLocalTranslation());
			vec.z += this.thickness;
		}

		return vec;
	}
}
