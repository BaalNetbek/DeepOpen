package AbyssEngine;

public final class Class_c53cameraRelated implements Class_121c {
   private GraphNode var_6c;
   private GraphNode var_94;
   private GraphNode var_106;
   private AEVector3D var_197;
   private AEVector3D var_265;
   private AEVector3D var_2b8;
   private AEVector3D var_2dc;
   private AEVector3D var_3fb;
   private boolean cameraFollowPlayer;
   private AEVector3D cameraRotation_;
   private AEVector3D cameraRotation2_;
   private Class_fd6 var_5e3;
   private boolean camerLockInPlace;
   private boolean nonFPScamera_;
   private AEVector3D var_6b7;
   private AEVector3D var_6ca;
   private AEVector3D var_705;
   private boolean var_767;
   private int var_7ab;
   private int var_7d8;
   private boolean var_82f;

   public Class_c53cameraRelated(GraphNode var1, GraphNode var2) {
      this(var1, var2, (AEVector3D)null);
   }

   private Class_c53cameraRelated(GraphNode var1, GraphNode var2, AEVector3D var3) {
      this.cameraRotation_ = new AEVector3D();
      this.cameraRotation2_ = new AEVector3D();
      this.camerLockInPlace = false;
      this.var_6b7 = new AEVector3D();
      this.var_6ca = new AEVector3D();
      this.var_705 = new AEVector3D();
      this.var_6c = var1;
      this.var_106 = var2;
      this.cameraFollowPlayer = true;
      this.var_3fb = null;
      if (this.var_197 == null) {
         this.var_197 = new AEVector3D();
      }

      if (this.var_265 == null) {
         this.var_265 = new AEVector3D();
      }

      if (this.var_2b8 == null) {
         this.var_2b8 = new AEVector3D();
      }

      if (this.var_2dc == null) {
         this.var_2dc = new AEVector3D();
      }

      if (var1 != null && var2 != null) {
         this.var_5e3 = new Class_fd6(var1, var2);

         for(this.var_94 = var1; this.var_94.sub_a4() != null; this.var_94 = this.var_94.sub_a4()) {
         }

         Object var4;
         for(var4 = var2; ((GraphNode)var4).sub_a4() != null; var4 = ((GraphNode)var4).sub_a4()) {
         }

         if (var4 == this.var_94) {
            this.var_94 = null;
         }

         this.nonFPScamera_ = var2 instanceof Camera;
         this.cameraRotation2_ = var2.sub_237().subtract(var1.sub_237(), new AEVector3D());
         this.var_82f = true;
      }
   }

   public final void sub_43(GraphNode var1) {
      this.var_6c = var1;
      if (this.var_5e3 == null && var1 != null) {
         this.var_5e3 = new Class_fd6(var1, this.var_106);
      }

      if (this.var_5e3 != null) {
         this.var_5e3.sub_36(var1);
      }

   }

   public final void sub_54(GraphNode var1) {
      this.var_106 = null;
      if (this.var_5e3 != null) {
         this.var_5e3.sub_68((GraphNode)null);
      }

      this.nonFPScamera_ = null instanceof Camera;
   }

   public final GraphNode sub_13c() {
      return this.var_106;
   }

   public final void sub_188(AEVector3D var1) {
      this.var_2b8.set(var1);
   }

   public final void sub_1df() {
      this.camerLockInPlace = true;
      this.var_2b8 = this.var_6c.sub_8a0().sub_a19(this.cameraRotation2_, this.var_2b8);
      this.var_106.sub_1f3(this.var_2b8);
   }

   public final void sub_249() {
      this.camerLockInPlace = false;
   }

   public final void sub_2aa() {
      if (this.var_82f) {
         this.var_82f = false;
         if (this.cameraFollowPlayer) {
            if (this.camerLockInPlace) {
               this.var_5e3.sub_2aa();
               return;
            }

            if (this.var_6c != null) {
               this.var_6ca.set(this.var_2dc);
               this.var_6b7.set(this.var_197);
               this.var_705.set(this.var_265);
               this.var_197 = this.var_6c.getUp();
               this.var_197.normalize();
               this.var_265 = this.var_6c.sub_8a0().sub_a19(this.cameraRotation2_, this.var_265);
               AEVector3D var10000 = this.var_2b8;
               var10000.x -= this.var_2b8.x - this.var_265.x >> 3;
               var10000 = this.var_2b8;
               var10000.y -= this.var_2b8.y - this.var_265.y >> 3;
               var10000 = this.var_2b8;
               var10000.z -= this.var_2b8.z - this.var_265.z >> 3;
               this.var_265 = this.var_6c.sub_8a0().sub_a19(this.cameraRotation_, this.var_265);
               var10000 = this.var_265;
               var10000.x -= this.var_2b8.x;
               var10000 = this.var_265;
               var10000.y -= this.var_2b8.y;
               var10000 = this.var_265;
               var10000.z -= this.var_2b8.z;
               if (this.nonFPScamera_) {
                  this.var_265.x = -this.var_265.x;
                  this.var_265.y = -this.var_265.y;
                  this.var_265.z = -this.var_265.z;
               }

               this.var_265.normalize();
               this.var_2dc = this.var_197.crossProduct(this.var_265, this.var_2dc);
               this.var_197 = this.var_265.crossProduct(this.var_2dc, this.var_197);
               this.var_2dc.normalize();
               this.var_106.sub_90e(this.var_2dc, this.var_197, this.var_265);
               if (this.var_767) {
                  if (this.var_7ab > 1000) {
                     this.var_767 = false;
                  }

                  int var1 = AEMath.max(1, (int)((float)(this.var_7d8 >> 1) * ((float)(1000 - this.var_7ab) / 1000.0F)));
                  var10000 = this.var_2b8;
                  var10000.x += -var1 + GameStatus.random.nextInt(var1 << 1);
                  var10000 = this.var_2b8;
                  var10000.y += -var1 + GameStatus.random.nextInt(var1 << 1);
                  var10000 = this.var_2b8;
                  var10000.z += -var1 + GameStatus.random.nextInt(var1 << 1);
               }

               this.var_106.sub_1f3(this.var_2b8);
            }
         }

      }
   }

   public final void sub_2e9(int var1) {
      this.var_7d8 = var1;
      this.var_7ab += var1;
      this.var_82f = true;
   }

   public final void sub_34a() {
      if (!this.var_767) {
         this.var_767 = true;
         this.var_7ab = 0;
      }

   }

   public final boolean sub_356() {
      return this.cameraFollowPlayer;
   }

   public final void sub_383(boolean var1) {
      this.cameraFollowPlayer = var1;
      this.var_5e3.sub_120(var1);
   }

   public final void sub_3a9(AEVector3D var1) {
      this.var_2b8.set(var1);
   }

   public final void sub_3dd(AEVector3D var1) {
      this.cameraRotation_.set(var1);
   }

   public final void sub_400(AEVector3D var1) {
      this.cameraRotation2_.set(var1);
   }
}
