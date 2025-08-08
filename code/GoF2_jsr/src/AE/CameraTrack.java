package AE;

import AE.PaintCanvas.AEGraphics3D;

public final class CameraTrack {
	private int time;
	private long frameTime;
	private final AECamera camera;
	private final CameraTrackInterpolator interpolator;
	private final int[][] cameraTracks = {
	      {
         0, 0, 0, 70000, 0, -300, 0, 800, 10000, 40000, 5000, -5000, -128, 1400, 0, 800, 20000, 0, 0, -70000, 0,
         2048, 0, 800, 40000, -45000, -5000, 40000, 128, 3072, 0, 800, 60000, 0, 0, 70000, 0, 3796, 0, 800
         },
	      {
         0, -1000, 600, -7000, 0, 2248, 0, 1000, 3000, 0, 600, -7000, -128, 2049, 0, 1000, 6000, 1000, 1000,
         -7000, -256, 1848, 0, 1000, 20000000, 1000, 600, -7000, -256, 1848, 0, 1000
         }
	      };

	public CameraTrack(final int var1) {
		this.camera = AECamera.create(GlobalStatus.screenWidth, GlobalStatus.screenHeight, 800, 100, AEGraphics3D.CAMERA_FAR);
		this.camera.setRotationOrder((short)2);
		this.time = 0;
		this.interpolator = new CameraTrackInterpolator(this.cameraTracks, var1, this.cameraTracks[var1][this.cameraTracks[var1].length - 8]);
	}

	public final void update(final long var1) {
		if (var1 <= 700L) {
			if (this.frameTime == 0L) {
				this.frameTime = var1;
				this.time = 0;
			}

			this.time = (int)(this.time + var1);
			this.interpolator.update(this.time);
			this.camera.moveTo(this.interpolator.currentPos.x, this.interpolator.currentPos.y, this.interpolator.currentPos.z);
			this.camera.setRotation(this.interpolator.currentRot.x, this.interpolator.currentRot.y, this.interpolator.currentRot.z);
			this.camera.setFoV(this.interpolator.currentFoV);
		}
	}

	public final AECamera getCamera() {
		return this.camera;
	}
}
