package AE;

import AE.Math.AEMath;
import AE.Math.AEVector3D;

public abstract class Camera extends AEGeometry {
	private int nearPlane;
	private int farPlane;
	private final AEVector3D[] vfPlaneNormals = new AEVector3D[6];
	private AEVector3D[] vfPlaneNormalsLocal = new AEVector3D[6];
	private final int[] lengths;
	private static AEVector3D cameraPos = new AEVector3D();
	private static AEVector3D nearPlaneCenter = new AEVector3D();
	private static AEVector3D farPlaneCenter = new AEVector3D();
	protected int screenWidth;
	protected int screenHeight;
	private int verticalProjectionFactor;
	private int horizontalProjectionFactor;

	protected Camera(final int w, final int h, final int fov, final int near, final int far) {
		for(int i = this.vfPlaneNormalsLocal.length - 1; i >= 0; --i) {
			this.vfPlaneNormalsLocal[i] = new AEVector3D();
		}

		this.lengths = new int[6];
		this.vfPlaneNormals[0] = new AEVector3D(0, 0, -AEMath.Q_1);
		this.vfPlaneNormals[1] = new AEVector3D(0, 0, AEMath.Q_1);
		this.vfPlaneNormals[2] = new AEVector3D();
		this.vfPlaneNormals[3] = new AEVector3D();
		this.vfPlaneNormals[4] = new AEVector3D();
		this.vfPlaneNormals[5] = new AEVector3D();
		this.screenWidth = w;
		this.screenHeight = h;
		setPerspective(fov, near, far);
	}

	public final void updateTransform(final boolean force) {
		if (this.transformDirty_ || force) {
			if (this.group != null) {
				this.localTransformation = this.group.localTransformation.multiplyTo(this.compositeTransformation, this.localTransformation);
			} else {
				this.localTransformation.set(this.compositeTransformation);
			}

			this.vfPlaneNormalsLocal = this.localTransformation.transformVectorsNoScale(this.vfPlaneNormals, this.vfPlaneNormalsLocal);
			cameraPos = this.localTransformation.getPosition(cameraPos);
			nearPlaneCenter = this.localTransformation.getDirection(nearPlaneCenter);
			farPlaneCenter.set(nearPlaneCenter);
			nearPlaneCenter.scale(-this.nearPlane);
			farPlaneCenter.scale(-this.farPlane);
			nearPlaneCenter.add(cameraPos);
			farPlaneCenter.add(cameraPos);
			this.lengths[0] = nearPlaneCenter.dot(this.vfPlaneNormalsLocal[0]);
			this.lengths[1] = farPlaneCenter.dot(this.vfPlaneNormalsLocal[1]);
			this.lengths[2] = cameraPos.dot(this.vfPlaneNormalsLocal[2]);
			this.lengths[3] = cameraPos.dot(this.vfPlaneNormalsLocal[3]);
			this.lengths[4] = cameraPos.dot(this.vfPlaneNormalsLocal[4]);
			this.lengths[5] = cameraPos.dot(this.vfPlaneNormalsLocal[5]);
			this.transformDirty_ = false;
			this.boundsDirty_ = false;
		}

	}

	public void setPerspective(int fov, int near, final int far) {
		this.nearPlane = near;
		this.farPlane = far;
		int sin = AEMath.sin(fov >> 1);
		int cos = AEMath.cos(fov >> 1);
		this.vfPlaneNormals[2].set(cos, 0, -sin);
		this.vfPlaneNormals[3].set(-cos, 0, -sin);
		this.vfPlaneNormals[4].set(0, -cos, -sin);
		this.vfPlaneNormals[5].set(0, cos, -sin);
		this.verticalProjectionFactor = (sin << AEMath.Q) / cos;
		this.horizontalProjectionFactor = this.verticalProjectionFactor * ((this.screenWidth << AEMath.Q) / this.screenHeight) >> AEMath.Q;
	}

	public final void setFoV(final int var1) {
		setPerspective(var1, this.nearPlane, this.farPlane);
	}

	public final boolean getScreenPosition(AEVector3D worldPos) {
		if ((worldPos = this.localTransformation.inverseTransformVector(worldPos)).z > this.nearPlane) {
			return false;
		}
		final int var2 = this.horizontalProjectionFactor * worldPos.z >> AEMath.Q;
		final int var3 = this.verticalProjectionFactor * worldPos.z >> AEMath.Q;
		if (var2 != 0 && var3 != 0) {
			// here stays 11 isntead of AEMath.Q-1 for now atleast as idk whats happening here
			// and in fixes projectn for higher Qs
			worldPos.x = -((worldPos.x << 11) / var2 * this.screenWidth >> AEMath.Q) + (this.screenWidth >> 1);
			worldPos.y = ((worldPos.y << 11) / var3 * this.screenHeight >> AEMath.Q) + (this.screenHeight >> 1);
			return worldPos.x >= 0 && worldPos.y >= 0 && worldPos.x < this.screenWidth && worldPos.y < this.screenHeight;
		} else {
			return false;
		}
	}

	public final byte isInViewFrustum(final AEBoundingSphere bsphere) {
		for(int i = 5; i >= 0; --i) {
			int var2 = bsphere.center.dot(this.vfPlaneNormalsLocal[i]) - this.lengths[i];
			if (var2 < -bsphere.radius) {
				return 0;
			}

			if (AEMath.abs(var2) < bsphere.radius) {
				return 1;
			}
		}

		return 2;
	}

	public final void appendToRender(final Camera var1, final Renderer var2) {
	}

	public final void forceAppendToRender(final Camera var1, final Renderer var2) {
	}

	public abstract void setActive();
	
	/**
	 * @param w - screen width
	 * @param h - screen height
	 * @param fov - in arbirtary -> 90/1024 degrees or just Q11 radians ffs
	 * @param near [plane]
	 * @param far [plane]
	 * @return new implementation Camera
	 */
	public static Camera create(final int w, final int h, final int fov, final int near, final int far) {
		return new JSRCamera(w, h, fov, near, far);
	}
}
