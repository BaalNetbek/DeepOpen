package AE;

import AE.Math.AEMath;
import AE.Math.AEVector3D;

public final class LookAtCamera implements CameraController_ {
	private GraphNode target;
	private GraphNode targetRoot;
	private int lockMode;
	private GraphNode camera;
	private static AEVector3D cameraRight;
	private static AEVector3D cameraDir;
	private static AEVector3D cameraUp;
	private AEVector3D upOverride;
	private boolean lookAtMode;

	public LookAtCamera(final GraphNode var1, final GraphNode var2) {
		this(var1, var2, (AEVector3D)null, 0);
	}

	private LookAtCamera(final GraphNode var1, final GraphNode var2, final AEVector3D var3, final int var4) {
		this.target = var1;
		this.camera = var2;
		this.lockMode = 0;
		this.lookAtMode = true;
		this.upOverride = null;
		if (cameraRight == null) {
			cameraRight = new AEVector3D();
		}

		if (cameraDir == null) {
			cameraDir = new AEVector3D();
		}

		if (cameraUp == null) {
			cameraUp = new AEVector3D();
		}

		for(this.targetRoot = var1; this.targetRoot.getGroup() != null; this.targetRoot = this.targetRoot.getGroup()) {
		}

		Object var5;
		for(var5 = var2; ((GraphNode)var5).getGroup() != null; var5 = ((GraphNode)var5).getGroup()) {
		}

		if (var5 == this.targetRoot) {
			this.targetRoot = null;
		}

	}

	public final void setTarget(final GraphNode var1) {
		this.target = var1;
	}

	public final void setCamera(final GraphNode var1) {
		this.camera = var1;
	}

	public final void setOrientationLock(final AEVector3D var1, final int var2) {
		this.upOverride = var1;
		this.lockMode = var2;
		if (var1 != null) {
			this.upOverride.normalize();
		}

	}

	public final void update() {
		if (this.lookAtMode) {
			if (this.targetRoot != null) {
				this.targetRoot.updateTransform(false);
			}

			cameraRight = this.target.getLocalPos(cameraRight);
			cameraRight = this.camera.getGroup().getLocalTransform().inverseTransformVector(cameraRight);
			(cameraDir = this.camera.getPosition(cameraDir)).subtract(cameraRight);
			cameraDir.normalize();
			if (this.upOverride != null) {
				cameraUp.set(this.upOverride);
			} else {
				cameraUp.set(0, AEMath.Q_1, 0);
			}

			(cameraRight = cameraUp.crossProduct(cameraDir, cameraRight)).normalize();
			if (AEMath.abs(cameraRight.x + cameraRight.y + cameraRight.z) < 4) {
				cameraUp.set(AEMath.Q_1, 0, 0);
				(cameraRight = cameraUp.crossProduct(cameraDir, cameraRight)).normalize();
			}

			switch(this.lockMode) {
			case 0:
			case 2:
				cameraUp = cameraDir.crossProduct(cameraRight, cameraUp);
				break;
			case 1:
				cameraDir = cameraRight.crossProduct(cameraUp, cameraDir);
			}

			this.camera.setTransform(cameraRight, cameraUp, cameraDir);
		}

	}

	public final boolean isInLookAtMode() {
		return this.lookAtMode;
	}

	public final void setLookAt(final boolean var1) {
		this.lookAtMode = var1;
	}
}
