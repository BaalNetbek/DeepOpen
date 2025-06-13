package AE;

import AE.Math.AEVector3D;
import AE.Math.Matrix;

public abstract class GraphNode {
   protected int animationFrameTime = 32;
   protected Group group = null;
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
   protected Matrix compositeTransformation;
   protected Matrix localTransformation;
   protected AEBoundingSphere boundingSphere;
   protected int resourceId;

   GraphNode() {
      this.draw = true;
      this.compositeTransformation = new Matrix();
      this.localTransformation = new Matrix();
      this.boundingSphere = new AEBoundingSphere();
      this.transformDirty_ = true;
      this.boundsDirty_ = true;
      this.resourceId = 0;
   }

   GraphNode(GraphNode var1) {
      this.draw = var1.draw;
      this.compositeTransformation = new Matrix(var1.compositeTransformation);
      this.localTransformation = new Matrix(var1.localTransformation);
      this.boundingSphere = new AEBoundingSphere(var1.boundingSphere);
      this.transformDirty_ = true;
      this.boundsDirty_ = true;
      this.resourceId = var1.resourceId;
   }

   public final int getID() {
      return this.resourceId;
   }

   public final void setDraw(boolean var1) {
      this.draw = var1;
   }

   public final boolean isVisible() {
      return this.draw;
   }

   public final Group getGroup() {
      return this.group;
   }

   public final GraphNode getParent() {
      return this.parent;
   }

   abstract void appendToRender(Camera var1, Renderer var2);

   abstract void forceAppendToRender(Camera var1, Renderer var2);

   public abstract void updateTransform(boolean var1);

   protected final void markDirty() {
      this.boundsDirty_ = true;
      if (this.group != null) {
         this.group.markDirty();
      }

   }

   public String toString() {
      return this.getString("" + this.resourceId, 0);
   }

   protected abstract String getString(String var1, int var2);

   public final void translate(int var1, int var2, int var3) {
      this.compositeTransformation.translate(var1, var2, var3);
      this.transformDirty_ = true;
      this.markDirty();
   }

   public final void translate(AEVector3D var1) {
      this.compositeTransformation.translate(var1);
      this.transformDirty_ = true;
      this.markDirty();
   }

   public void moveTo(int var1, int var2, int var3) {
      this.compositeTransformation.translateTo(var1, var2, var3);
      this.transformDirty_ = true;
      this.markDirty();
   }

   public final void moveTo(AEVector3D var1) {
      this.compositeTransformation.translateTo(var1);
      this.transformDirty_ = true;
      this.markDirty();
   }

   public final void moveForward(int var1) {
      this.compositeTransformation.translateForward(var1);
      this.transformDirty_ = true;
      this.markDirty();
   }

   public final AEVector3D getPosition(AEVector3D var1) {
      return this.compositeTransformation.getPosition(var1);
   }

   public final AEVector3D getLocalPos(AEVector3D var1) {
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

   public final AEVector3D getDirection(AEVector3D var1) {
      return this.compositeTransformation.getDirection(var1);
   }

   public final AEVector3D getLocalDirection(AEVector3D var1) {
      return this.localTransformation.getDirection(var1);
   }

   public final AEVector3D getDirection() {
      return this.compositeTransformation.getDirection();
   }

   public final AEVector3D getLocalDirection() {
      return this.localTransformation.getDirection();
   }

   public final AEVector3D getUp(AEVector3D var1) {
      return this.compositeTransformation.getUp(var1);
   }

   public final AEVector3D getUp() {
      return this.compositeTransformation.getUp();
   }

   public final AEVector3D getRightVector(AEVector3D var1) {
      return this.compositeTransformation.getRight(var1);
   }

   public final AEVector3D getRight() {
      return this.compositeTransformation.getRight();
   }

   public final void setRotationOrder(short var1) {
      this.compositeTransformation.setRotationOrder(var1);
      this.localTransformation.setRotationOrder(var1);
   }

   public final void rotateEuler(int var1, int var2, int var3) {
      this.compositeTransformation.addEulerAngles(var1, var2, var3);
      this.transformDirty_ = true;
      this.markDirty();
   }

   public final void roll(int var1) {
      this.compositeTransformation.rotateAroundDir(var1);
      this.transformDirty_ = true;
      this.markDirty();
   }

   public final void pitch(int var1) {
      this.compositeTransformation.rotateAroundRight(var1);
      this.transformDirty_ = true;
      this.markDirty();
   }

   public final void yaw(int var1) {
      this.compositeTransformation.rotateAroundUp(var1);
      this.transformDirty_ = true;
      this.markDirty();
   }

   public final void setRotation(int var1, int var2, int var3) {
      this.compositeTransformation.setRotation(var1, var2, var3);
      this.transformDirty_ = true;
      this.markDirty();
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

   public final void setScale(int var1, int var2, int var3) {
      this.compositeTransformation.setScale(var1, var2, var3);
      this.transformDirty_ = true;
      this.markDirty();
   }

   public final AEVector3D copyScaleTo(AEVector3D var1) {
      return this.compositeTransformation.copyScaleTo(var1);
   }

   public final AEVector3D getScale() {
      return this.compositeTransformation.getScale();
   }

   public final Matrix getToParentTransform() {
      return this.compositeTransformation;
   }

   public final Matrix getLocalTransform() {
      return this.localTransformation;
   }

   public final void setTransform(Matrix var1) {
      this.compositeTransformation.set(var1);
      this.transformDirty_ = true;
      this.markDirty();
   }

   public final Matrix getInverseTransform(Matrix var1) {
      return this.compositeTransformation.getInverse(var1);
   }

   public final void setTransform(AEVector3D var1, AEVector3D var2, AEVector3D var3) {
      this.compositeTransformation.setRows(var1, var2, var3);
      this.transformDirty_ = true;
      this.markDirty();
   }

   public void setAnimationSpeed(int var1) {
   }

   public int getCurrentAnimFrame() {
      return 0;
   }

   public void setAnimationRangeInTime(int var1, int var2) {
   }

   public void setAnimationMode(byte var1) {
   }

   public void disableAnimation() {
   }

   public boolean hasAnimation() {
      return false;
   }
}
