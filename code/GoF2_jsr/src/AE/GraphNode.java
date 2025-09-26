package AE;

import AE.Math.AEVector3D;
import AE.Math.AEMatrix;

public abstract class GraphNode {
	protected int animationFrameTime = 32;
	protected AEGroup group = null;
	protected GraphNode parent;
	protected boolean draw;
	protected boolean transformDirty_;
	protected boolean boundsDirty_;
	/**
	 * To parent transformation matrix.
	 *
	 * The composite transformation is defined to be such that it transforms a point in the local coordinate system of this node to the coordinate system of the given node.
	 * ^ from JSR-184 documentation (javax.microedition.m3g.Node.getTransformTo)
	 */
	protected AEMatrix compositeTransformation;
	protected AEMatrix localTransformation;
	protected AEBoundingSphere boundingSphere;
	protected int resourceId;

	GraphNode() {
		this.draw = true;
		this.compositeTransformation = new AEMatrix();
		this.localTransformation = new AEMatrix();
		this.boundingSphere = new AEBoundingSphere();
		this.transformDirty_ = true;
		this.boundsDirty_ = true;
		this.resourceId = 0;
	}

	GraphNode(final GraphNode var1) {
		this.draw = var1.draw;
		this.compositeTransformation = new AEMatrix(var1.compositeTransformation);
		this.localTransformation = new AEMatrix(var1.localTransformation);
		this.boundingSphere = new AEBoundingSphere(var1.boundingSphere);
		this.transformDirty_ = true;
		this.boundsDirty_ = true;
		this.resourceId = var1.resourceId;
	}

	public final int getID() {
		return this.resourceId;
	}

	public final void setDraw(final boolean draw) {
		this.draw = draw;
	}

	public final boolean isVisible() {
		return this.draw;
	}

	public final AEGroup getGroup() {
		return this.group;
	}

	public final GraphNode getParent() {
		return this.parent;
	}

	abstract void appendToRender(AECamera var1, Renderer var2);

	abstract void forceAppendToRender(AECamera var1, Renderer var2);

	public abstract void updateTransform(boolean var1);

	protected final void markDirty() {
		this.boundsDirty_ = true;
		if (this.group != null) {
			this.group.markDirty();
		}

	}

	public String toString() {
		return getString("" + this.resourceId, 0);
	}

	protected abstract String getString(String var1, int var2);

	public final void translate(final int var1, final int var2, final int var3) {
		this.compositeTransformation.translate(var1, var2, var3);
		this.transformDirty_ = true;
		markDirty();
	}

	public final void translate(final AEVector3D var1) {
		this.compositeTransformation.translate(var1);
		this.transformDirty_ = true;
		markDirty();
	}

	public void moveTo(final int x, final int y, final int z) {
		this.compositeTransformation.translateTo(x, y, z);
		this.transformDirty_ = true;
		markDirty();
	}

	public final void moveTo(final AEVector3D var1) {
		this.compositeTransformation.translateTo(var1);
		this.transformDirty_ = true;
		markDirty();
	}

	public final void moveForward(final int var1) {
		this.compositeTransformation.translateForward(var1);
		this.transformDirty_ = true;
		markDirty();
	}

	public final AEVector3D getPosition(final AEVector3D var1) {
		return this.compositeTransformation.getPosition(var1);
	}

	public final AEVector3D getLocalPos(final AEVector3D var1) {
		return this.localTransformation.getPosition(var1);
	}

	public final AEVector3D getPostition() {
		return this.compositeTransformation.getPosition();
	}

	public final AEVector3D getLocalPos() {
		return this.localTransformation.getPosition();
	}

	public final int getPosX() {
		return this.compositeTransformation.getPositionX();
	}

	public final int getPosY() {
		return this.compositeTransformation.getPositionY();
	}

	public final int getPosZ() {
		return this.compositeTransformation.getPositionZ();
	}

	public final int getLocalPosZ() {
		return this.localTransformation.getPositionZ();
	}

	public final AEVector3D getDirection(final AEVector3D var1) {
		return this.compositeTransformation.getDirection(var1);
	}

	public final AEVector3D getLocalDirection(final AEVector3D var1) {
		return this.localTransformation.getDirection(var1);
	}

	public final AEVector3D getDirection() {
		return this.compositeTransformation.getDirection();
	}

	public final AEVector3D getLocalDirection() {
		return this.localTransformation.getDirection();
	}

	public final AEVector3D getUp(final AEVector3D var1) {
		return this.compositeTransformation.getUp(var1);
	}

	public final AEVector3D getUp() {
		return this.compositeTransformation.getUp();
	}

	public final AEVector3D getRightVector(final AEVector3D var1) {
		return this.compositeTransformation.getRight(var1);
	}

	public final AEVector3D getRight() {
		return this.compositeTransformation.getRight();
	}

	public final void setRotationOrder(final short var1) {
		this.compositeTransformation.setRotationOrder(var1);
		this.localTransformation.setRotationOrder(var1);
	}

	public final void rotateEuler(final int x, final int y, final int z) {
		this.compositeTransformation.addEulerAngles(x, y, z);
		this.transformDirty_ = true;
		markDirty();
	}

	public final void roll(final int var1) {
		this.compositeTransformation.rotateAroundDir(var1);
		this.transformDirty_ = true;
		markDirty();
	}

	public final void pitch(final int var1) {
		this.compositeTransformation.rotateAroundRight(var1);
		this.transformDirty_ = true;
		markDirty();
	}

	public final void yaw(final int var1) {
		this.compositeTransformation.rotateAroundUp(var1);
		this.transformDirty_ = true;
		markDirty();
	}

	public final void setRotation(final int var1, final int var2, final int var3) {
		this.compositeTransformation.setRotation(var1, var2, var3);
		this.transformDirty_ = true;
		markDirty();
	}

	public final int getEulerX() {
		return this.compositeTransformation.getEulerX();
	}

	public final int getEulerY() {
		return this.compositeTransformation.getEulerY();
	}

	public final int getEulerZ() {
		return this.compositeTransformation.getEulerZ();
	}

	public final void setScale(final int var1, final int var2, final int var3) {
		this.compositeTransformation.setScale(var1, var2, var3);
		this.transformDirty_ = true;
		markDirty();
	}

	public final AEVector3D copyScaleTo(final AEVector3D var1) {
		return this.compositeTransformation.copyScaleTo(var1);
	}

	public final AEVector3D getScale() {
		return this.compositeTransformation.getScale();
	}

	public final AEMatrix getToParentTransform() {
		return this.compositeTransformation;
	}

	public final AEMatrix getLocalTransform() {
		return this.localTransformation;
	}

	public final void setTransform(final AEMatrix var1) {
		this.compositeTransformation.set(var1);
		this.transformDirty_ = true;
		markDirty();
	}

	public final AEMatrix getInverseTransform(final AEMatrix var1) {
		return this.compositeTransformation.getInverse(var1);
	}

	public final void setTransform(final AEVector3D var1, final AEVector3D var2, final AEVector3D var3) {
		this.compositeTransformation.setRows(var1, var2, var3);
		this.transformDirty_ = true;
		markDirty();
	}

	public void setAnimationSpeed(final int var1) {
	}

	public int getCurrentAnimFrame() {
		return 0;
	}

	public void setAnimationRangeInTime(final int var1, final int var2) {
	}

	public void setAnimationMode(final byte var1) {
	}

	public void disableAnimation() {
	}

	public boolean hasAnimation() {
		return false;
	}
}
