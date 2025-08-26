package AE;

import AE.Math.AEMath;
import AE.Math.AEVector3D;

public abstract class SceneObject extends GraphNode {
	private static AEVector3D tempCenter = new AEVector3D();
	protected int radius = 0;

	public SceneObject() {
	}

	public SceneObject(final SceneObject var1) {
		super(var1);
		this.radius = var1.radius;
	}

	public void updateTransform(final boolean var1) {
		if (this.transformDirty_ || var1) {
			if (this.group != null) {
				this.localTransformation = this.group.localTransformation.multiplyTo(this.compositeTransformation, this.localTransformation);
			} else {
				this.localTransformation.set(this.compositeTransformation);
			}
			tempCenter = this.localTransformation.copyScaleTo(tempCenter);
			final int r = AEMath.max(AEMath.max(
        			AEMath.abs(tempCenter.x),
        			AEMath.abs(tempCenter.y)),
        			AEMath.abs(tempCenter.z)
			) * this.radius >> AEMath.Q;
			tempCenter = this.localTransformation.getPosition(tempCenter);
			this.boundingSphere.setXYZR(tempCenter.x, tempCenter.y, tempCenter.z, r);
			this.transformDirty_ = false;
			this.boundsDirty_ = false;
		}

	}

	public final void setRadius(final int var1) {
		this.radius = var1;
	}

	protected final String getString(String var1, final int len) {
		for(int i = 0; i < len; ++i) {
			var1 = var1 + "  ";
		}

		return var1 + "\n";
	}
}
