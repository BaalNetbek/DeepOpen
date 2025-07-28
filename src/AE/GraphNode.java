package AE;

import AE.Math.AEQuaternion;
import AE.Math.AEVector3D;
import AE.Math.Matrix;

public abstract class GraphNode {
   protected int animationFrameTime = 32;
   protected Group group = null;
   protected GraphNode parent;
   protected boolean draw;
   protected boolean transformDirty_;
   protected boolean boundsDirty_;
   protected Matrix globalTransform;
   protected Matrix tempTransform;
   protected AEBoundingSphere boundingSphere;
   protected int resourceId;

   GraphNode() {
      this.draw = true;
      this.globalTransform = new Matrix();
      this.tempTransform = new Matrix();
      this.boundingSphere = new AEBoundingSphere();
      this.transformDirty_ = true;
      this.boundsDirty_ = true;
      this.resourceId = 0;
   }

   GraphNode(GraphNode var1) {
      this.draw = var1.draw;
      this.globalTransform = new Matrix(var1.globalTransform);
      this.tempTransform = new Matrix(var1.tempTransform);
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
      this.globalTransform.translate(var1, var2, var3);
      this.transformDirty_ = true;
      this.markDirty();
   }

   public final void translate(AEVector3D var1) {
      this.globalTransform.translate(var1);
      this.transformDirty_ = true;
      this.markDirty();
   }

   public void moveTo(int var1, int var2, int var3) {
      this.globalTransform.translateTo(var1, var2, var3);
      this.transformDirty_ = true;
      this.markDirty();
   }

   public final void moveTo(AEVector3D var1) {
      this.globalTransform.translateTo(var1);
      this.transformDirty_ = true;
      this.markDirty();
   }

   public final void moveForward(int var1) {
      this.globalTransform.translateForward(var1);
      this.transformDirty_ = true;
      this.markDirty();
   }

   public final AEVector3D getPosition(AEVector3D var1) {
      return this.globalTransform.getPosition(var1);
   }

   public final AEVector3D getLocalPos(AEVector3D var1) {
      return this.tempTransform.getPosition(var1);
   }

   public final AEVector3D getPostition() {
      return this.globalTransform.getPosition();
   }

   public final AEVector3D getLocalPos() {
      return this.tempTransform.getPosition();
   }

   public final int getPosX() {
      return this.globalTransform.getPositionX();
   }

   public final int getPosY() {
      return this.globalTransform.getPositionY();
   }

   public final int getPosZ() {
      return this.globalTransform.getPositionZ();
   }

   public final int getLocalPosZ() {
      return this.tempTransform.getPositionZ();
   }

   public final AEVector3D getDirection(AEVector3D var1) {
      return this.globalTransform.getDirection(var1);
   }

   public final AEVector3D getLocalDirection(AEVector3D var1) {
      return this.tempTransform.getDirection(var1);
   }

   public final AEVector3D getDirection() {
      return this.globalTransform.getDirection();
   }

   public final AEVector3D getLocalDirection() {
      return this.tempTransform.getDirection();
   }

   public final AEVector3D getUp(AEVector3D var1) {
      return this.globalTransform.getUp(var1);
   }

   public final AEVector3D getUp() {
      return this.globalTransform.getUp();
   }

   public final AEVector3D getRightVector(AEVector3D var1) {
      return this.globalTransform.getRight(var1);
   }

   public final AEVector3D getRight() {
      return this.globalTransform.getRight();
   }

   public final void setRotationOrder(short var1) {
      this.globalTransform.setRotationOrder(var1);
      this.tempTransform.setRotationOrder(var1);
   }

   public final void rotateEuler(int var1, int var2, int var3) {
      this.globalTransform.addEulerAngles(var1, var2, var3);
      this.transformDirty_ = true;
      this.markDirty();
   }

   public final void roll(int var1) {
      this.globalTransform.rotateAroundDir(var1);
      this.transformDirty_ = true;
      this.markDirty();
   }

   public final void pitch(int var1) {
      this.globalTransform.rotateAroundRight(var1);
      this.transformDirty_ = true;
      this.markDirty();
   }

   public final void yaw(int var1) {
      this.globalTransform.rotateAroundUp(var1);
      this.transformDirty_ = true;
      this.markDirty();
   }

   public final void setRotation(int var1, int var2, int var3) {
      this.globalTransform.setRotation(var1, var2, var3);
      this.transformDirty_ = true;
      this.markDirty();
   }

   public final int getEulerX() {
      return this.globalTransform.getEulerX();
   }

   public final int getEulerY() {
      return this.globalTransform.getEulerY();
   }

   public final int getEulerZ() {
      return this.globalTransform.getEulerZ();
   }

   public final void setToParentFromQuaterion(AEQuaternion var1) {
       this.globalTransform.fromQuaternion(var1);
       this.transformDirty_ = true;
       this.markDirty();
    }
   
   public final void setScale(int var1, int var2, int var3) {
      this.globalTransform.setScale(var1, var2, var3);
      this.transformDirty_ = true;
      this.markDirty();
   }

   public final AEVector3D copyScaleTo(AEVector3D var1) {
      return this.globalTransform.copyScaleTo(var1);
   }

   public final AEVector3D getScale() {
      return this.globalTransform.getScale();
   }

   public final Matrix getToParentTransform() {
      return this.globalTransform;
   }

   public final Matrix getLocalTransform() {
      return this.tempTransform;
   }

   public final void setTransform(Matrix var1) {
      this.globalTransform.set(var1);
      this.transformDirty_ = true;
      this.markDirty();
   }

   public final Matrix getInverseTransform(Matrix var1) {
      return this.globalTransform.getInverse(var1);
   }

   public final void setTransform(AEVector3D var1, AEVector3D var2, AEVector3D var3) {
      this.globalTransform.setRows(var1, var2, var3);
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
   
   public Matrix getTempTransformNegative() {
       return this.tempTransform.getNegative();
   }
}
