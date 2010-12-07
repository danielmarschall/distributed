package test.fraktal3d;
import java.awt.Color;

import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.shape.Box;
import com.jme.scene.state.BlendState;
import com.jme.scene.state.MaterialState;
import com.jme.system.DisplaySystem;

public class Raumplan {
	protected Box centerbox;
	
	private static final float Size_Faktor = 0.5f;
	private static final float Abstand_Faktor = 0.5f;
	private static final float Max_Iter = 6; // Ab 7 wird's eklig!
	
	public Raumplan(Node rootNode, float size, float abstand, float x, float y, float z, LockDirectoryEnum e, int iter) {
		if (iter > Max_Iter) return;
		
		Box centerbox = new Box("Center-Box", new Vector3f(x, y, z), size, size, size);
		
		Node rn = getRoomNode(centerbox, iter);
		rootNode.attachChild(rn);
		
		// WIESO geht es nur mit abstand??? Es sollte abstand*1 sein!
		float abstand2 = 0.5f*size + abstand + 0.5f*size*Size_Faktor;
//		System.out.println(iter);
//		System.out.println(size);
//		System.out.println((0.5f*size) + " + " + abstand + " + " + ( 0.5f*size*Size_Faktor));
//		
//		System.out.println(x-abstand2);
		
		if (e != LockDirectoryEnum.LOCK_X_NEG)
			new Raumplan(rn, size*Size_Faktor, abstand*Abstand_Faktor, x-abstand2, y, z, LockDirectoryEnum.LOCK_X_POS, iter+1);
		if (e != LockDirectoryEnum.LOCK_X_POS)
			new Raumplan(rn, size*Size_Faktor, abstand*Abstand_Faktor, x+abstand2, y, z, LockDirectoryEnum.LOCK_X_NEG, iter+1);
		if (e != LockDirectoryEnum.LOCK_Y_NEG)
			new Raumplan(rn, size*Size_Faktor, abstand*Abstand_Faktor, x, y-abstand2, z, LockDirectoryEnum.LOCK_Y_POS, iter+1);
		if (e != LockDirectoryEnum.LOCK_Y_POS)
			new Raumplan(rn, size*Size_Faktor, abstand*Abstand_Faktor, x, y+abstand2, z, LockDirectoryEnum.LOCK_Y_NEG, iter+1);
		if (e != LockDirectoryEnum.LOCK_Z_NEG)
			new Raumplan(rn, size*Size_Faktor, abstand*Abstand_Faktor, x, y, z-abstand2, LockDirectoryEnum.LOCK_Z_POS, iter+1);
		if (e != LockDirectoryEnum.LOCK_Z_POS)
			new Raumplan(rn, size*Size_Faktor, abstand*Abstand_Faktor, x, y, z+abstand2, LockDirectoryEnum.LOCK_Z_NEG, iter+1);
	}
	
	protected static Node getRoomNode(Box centerbox, int iter) {
		Node roomNode = new Node(); // Unnötiges Zwischennode?
		
		float opacityAmount = 1.0f;
		DisplaySystem display = DisplaySystem.getDisplaySystem();
		
		MaterialState materialState = display.getRenderer()
		.createMaterialState();

        // the sphere material that will be modified to make the sphere
        // look opaque then transparent then opaque and so on
        materialState = display.getRenderer().createMaterialState();
        materialState.setAmbient(new ColorRGBA(0.0f, 0.0f, 0.0f, opacityAmount));
        
        Color x = ColorUtilities.HSLtoRGB(1.0f/iter, 1.0f, 0.5f);
        float r = (float)x.getRed()/255;
        float g = (float)x.getGreen()/255;
        float b = (float)x.getBlue()/255;
        
        materialState.setDiffuse(new ColorRGBA(r, g, b, opacityAmount)); // TODO
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
		
        roomNode.attachChild(centerbox);
        return roomNode;
	}

}
