package AE;

import javax.microedition.m3g.Transform;

import AE.PaintCanvas.AEGraphics3D;

public final class JSRCamera extends Camera {
	private javax.microedition.m3g.Camera camera;
	private static Transform transform = new Transform();

	public JSRCamera(final int var1, final int var2, final int var3, final int var4, final int var5) {
		super(var1, var2, var3, var4, var5);
	}

	public final void setPerspective(final int var1, final int var2, final int var3) {
		float var4 = 0.8F;
		super.setPerspective(var1, var2, var3);
		if (this.camera == null) {
			this.camera = new javax.microedition.m3g.Camera();
		}

		if (this.screenWidth != 0 && this.screenHeight != 0) {
			var4 = (float)this.screenWidth / (float)this.screenHeight;
		}

		this.camera.setPerspective(var1 * 0.087890625F, var4, var2, var3);
	}

	public final void setActive() {
		if (AEGraphics3D.graphics3D != null) {
			AEGraphics3D.graphics3D.setCamera(this.camera, transform);
		}

	}
}
