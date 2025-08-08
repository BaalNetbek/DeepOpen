package AE;

import javax.microedition.m3g.Transform;

import AE.PaintCanvas.AEGraphics3D;

public final class JSRCamera extends Camera {
	private javax.microedition.m3g.Camera camera;
	private static Transform transform = new Transform();

	public JSRCamera(final int w, final int h, final int fov, final int near, final int far) {
		super(w, h, fov, near, far);
	}

	public final void setPerspective(final int fov, final int near, final int far) {
		float aspectRatio = 0.8F;
		super.setPerspective(fov, near, far);
		if (this.camera == null) {
			this.camera = new javax.microedition.m3g.Camera();
		}

		if (this.screenWidth != 0 && this.screenHeight != 0) {
			aspectRatio = (float)this.screenWidth / (float)this.screenHeight;
		}

		this.camera.setPerspective(fov * 90.0f/1024.0f, aspectRatio, near, far);
	}

	public final void setActive() {
		if (AEGraphics3D.graphics3D != null) {
			AEGraphics3D.graphics3D.setCamera(this.camera, transform);
		}

	}
}
