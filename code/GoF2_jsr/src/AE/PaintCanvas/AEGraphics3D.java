package AE.PaintCanvas;

import javax.microedition.lcdui.Graphics;
import javax.microedition.m3g.Background;
import javax.microedition.m3g.Graphics3D;
import javax.microedition.m3g.Light;
import javax.microedition.m3g.Transform;

public final class AEGraphics3D extends IGraphics3D {
	public static final int CLAMP_TOP = 20000; //stations, 
	public static final int CLAMP_MID = 28000; //wormholes, big ships, jumpgates
	public static final int CLAMP_BOT = 30000; //asteroids
	public static final int CAMERA_FAR = 31768;
	public static final int CAMERA_FAR_2 = 32000;
	public static Graphics3D graphics3D;
	private static Background background;

	public AEGraphics3D() {
		graphics3D = Graphics3D.getInstance();
		Light var1;
		(var1 = new Light()).setIntensity(1.0F);
		var1.setMode(129);
		Light var2;
		(var2 = new Light()).setIntensity(2.0F);
		var2.setMode(128);
		final Transform var3 = new Transform();
		final Transform var4 = new Transform();
		graphics3D.addLight(var1, var3);
		graphics3D.addLight(var2, var4);
		if (background == null) {
			(background = new Background()).setColorClearEnable(false);
			background.setDepthClearEnable(true);
		}

	}

	public final void bindTarget(final Graphics var1) {
		try {
			graphics3D.bindTarget(var1);
		} catch (final Exception var2) {
			graphics3D.releaseTarget();
		}
	}

	public final void clear() {
		try {
			graphics3D.clear(background);
		} catch (final Exception var1) {
		}
	}

	public final void releaseTarget() {
		try {
			graphics3D.releaseTarget();
		} catch (final Exception var1) {
		}
	}
}
