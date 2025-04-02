package AbyssEngine;

public abstract class GraphNode {
   protected int var_45 = 32;
   protected Group meshGroup = null;
   protected GraphNode parent;
   protected boolean draw;
   protected boolean var_c1;
   protected boolean var_ee;
   protected Matrix currentTransform;
   protected Matrix var_14c;
   protected AEBoundingSphere var_19f;
   protected int resourceId;

   GraphNode() {
      this.draw = true;
      this.currentTransform = new Matrix();
      this.var_14c = new Matrix();
      this.var_19f = new AEBoundingSphere();
      this.var_c1 = true;
      this.var_ee = true;
      this.resourceId = 0;
   }

   GraphNode(GraphNode var1) {
      this.draw = var1.draw;
      this.currentTransform = new Matrix(var1.currentTransform);
      this.var_14c = new Matrix(var1.var_14c);
      this.var_19f = new AEBoundingSphere(var1.var_19f);
      this.var_c1 = true;
      this.var_ee = true;
      this.resourceId = var1.resourceId;
   }

   public final int sub_a() {
      return this.resourceId;
   }

   public final void setDraw(boolean var1) {
      this.draw = var1;
   }

   public final boolean sub_87() {
      return this.draw;
   }

   public final Group sub_a4() {
      return this.meshGroup;
   }

   public final GraphNode sub_007() {
      return this.parent;
   }

   abstract void sub_11b(Camera var1, Class_db var2);

   abstract void sub_b2(Camera var1, Class_db var2);

   public abstract void sub_109(boolean var1);

   protected final void sub_117() {
      this.var_ee = true;
      if (this.meshGroup != null) {
         this.meshGroup.sub_117();
      }

   }

   public String toString() {
      return this.getString("" + this.resourceId, 0);
   }

   protected abstract String getString(String var1, int var2);

   public final void sub_18f(int var1, int var2, int var3) {
      this.currentTransform.translate(var1, var2, var3);
      this.var_c1 = true;
      this.sub_117();
   }

   public final void sub_19c(AEVector3D var1) {
      this.currentTransform.translate(var1);
      this.var_c1 = true;
      this.sub_117();
   }

   public void moveTo(int var1, int var2, int var3) {
      this.currentTransform.translateTo(var1, var2, var3);
      this.var_c1 = true;
      this.sub_117();
   }

   public final void sub_1f3(AEVector3D var1) {
      this.currentTransform.translateTo(var1);
      this.var_c1 = true;
      this.sub_117();
   }

   public final void sub_202(int var1) {
      this.currentTransform.translateForward(var1);
      this.var_c1 = true;
      this.sub_117();
   }

   public final AEVector3D sub_216(AEVector3D var1) {
      return this.currentTransform.getPosition(var1);
   }

   public final AEVector3D sub_22a(AEVector3D var1) {
      return this.var_14c.getPosition(var1);
   }

   public final AEVector3D sub_237() {
      return this.currentTransform.getPosition();
   }

   public final AEVector3D sub_28c() {
      return this.var_14c.getPosition();
   }

   public final int sub_29c() {
      return this.currentTransform.getPositionX();
   }

   public final int sub_2c5() {
      return this.currentTransform.getPositionY();
   }

   public final int sub_31f() {
      return this.currentTransform.getPositionZ();
   }

   public final int sub_32d() {
      return this.var_14c.getPositionZ();
   }

   public final AEVector3D sub_36a(AEVector3D var1) {
      return this.currentTransform.getDirection(var1);
   }

   public final AEVector3D sub_3b2(AEVector3D var1) {
      return this.var_14c.getDirection(var1);
   }

   public final AEVector3D getDirection() {
      return this.currentTransform.getDirection();
   }

   public final AEVector3D sub_462() {
      return this.var_14c.getDirection();
   }

   public final AEVector3D getUp(AEVector3D var1) {
      return this.currentTransform.getUp(var1);
   }

   public final AEVector3D getUp() {
      return this.currentTransform.getUp();
   }

   public final AEVector3D sub_548(AEVector3D var1) {
      return this.currentTransform.getRight(var1);
   }

   public final AEVector3D getRight() {
      return this.currentTransform.getRight();
   }

   public final void sub_5a7(short var1) {
      this.currentTransform.set_Type(var1);
      this.var_14c.set_Type(var1);
   }

   public final void sub_5c5(int var1, int var2, int var3) {
      this.currentTransform.addEulerAngles(var1, var2, var3);
      this.var_c1 = true;
      this.sub_117();
   }

   public final void roll(int var1) {
      this.currentTransform.rotateAroundDir(var1);
      this.var_c1 = true;
      this.sub_117();
   }

   public final void pitch(int var1) {
      this.currentTransform.rotateAroundRight(var1);
      this.var_c1 = true;
      this.sub_117();
   }

   public final void yaw(int var1) {
      this.currentTransform.rotateAroundUp(var1);
      this.var_c1 = true;
      this.sub_117();
   }

   public final void setRotation(int var1, int var2, int var3) {
      this.currentTransform.setRotationEuler(var1, var2, var3);
      this.var_c1 = true;
      this.sub_117();
   }

   public final int sub_72c() {
      return this.currentTransform.sub_752();
   }

   public final int sub_753() {
      return this.currentTransform.sub_7a8();
   }

   public final int sub_766() {
      return this.currentTransform.sub_7f1();
   }

   public final void sub_7af(int var1, int var2, int var3) {
      this.currentTransform.setScale(var1, var2, var3);
      this.var_c1 = true;
      this.sub_117();
   }

   public final AEVector3D sub_7ff(AEVector3D var1) {
      return this.currentTransform.sub_882(var1);
   }

   public final AEVector3D sub_821() {
      return this.currentTransform.sub_89b();
   }

   public final Matrix sub_85f() {
      return this.currentTransform;
   }

   public final Matrix sub_8a0() {
      return this.var_14c;
   }

   public final void sub_8c9(Matrix var1) {
      this.currentTransform.set(var1);
      this.var_c1 = true;
      this.sub_117();
   }

   public final Matrix sub_8f0(Matrix var1) {
      return this.currentTransform.sub_8ac(var1);
   }

   public final void sub_90e(AEVector3D var1, AEVector3D var2, AEVector3D var3) {
      this.currentTransform.sub_8f5(var1, var2, var3);
      this.var_c1 = true;
      this.sub_117();
   }

   public void sub_918(int var1) {
   }

   public int sub_942() {
      return 0;
   }

   public void sub_980(int var1, int var2) {
   }

   public void sub_9aa(byte var1) {
   }

   public void sub_a04() {
   }

   public boolean sub_a37() {
      return false;
   }
}
