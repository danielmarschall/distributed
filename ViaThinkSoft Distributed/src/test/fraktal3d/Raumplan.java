package test.fraktal3d;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.shape.Box;
import com.jme.scene.state.BlendState;
import com.jme.scene.state.MaterialState;
import com.jme.system.DisplaySystem;

public class Raumplan {
	private Box centerbox;
	
	private static final int Abbruch_Size = 5;
	private static final float Size_Faktor = 0.5f;
	private static final float Abstand_Initial = 0.5f;
	// private static final float Abstand_Faktor = 0.5f;
	
	public Raumplan(Node rootNode, float size, float x, float y, float z, LockDirectoryEnum e) {
		this.centerbox = new Box("Center-Box", new Vector3f(x, y, z), new Vector3f(size, size, size));
	}
	
	public void los(Node rootNode, float size, float x, float y, float z, LockDirectoryEnum e) {
		if (size > Abbruch_Size) {		
			new Raumplan(getRoomNode(), size, x-Abstand_Initial, y-Abstand_Initial, z-Abstand_Initial, LockDirectoryEnum.LOCK_X_POS).los(this.getRoomNode(), size*Size_Faktor, x-Abstand_Initial, y-Abstand_Initial, z-Abstand_Initial, LockDirectoryEnum.LOCK_X_POS);
		}		

		rootNode.attachChild(getRoomNode());
	}

	protected Node getRoomNode() {
		Node roomNode = new Node();
		
		float opacityAmount = 1.0f;
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
 
        centerbox.setRenderState(materialState);
        centerbox.updateRenderState();
 
        roomNode.attachChild(centerbox);
 
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
 
        centerbox.setRenderState(alphaState);
        centerbox.updateRenderState();
		
        return roomNode;
	}

}
