package AbyssEngine;

public abstract class Camera extends Class_13a3 {
   private int var_a1;
   private int var_c5;
   private AEVector3D[] vec3_arr1 = new AEVector3D[6];
   private AEVector3D[] vec3_arr2 = new AEVector3D[6];
   private int[] lengths;
   private static AEVector3D vec1 = new AEVector3D();
   private static AEVector3D vec3_2 = new AEVector3D();
   private static AEVector3D vec3_3 = new AEVector3D();
   protected int screenWidth;
   protected int screenHeight;
   private int var_23d;
   private int var_25c;

   protected Camera(int var1, int var2, int var3, int var4, int var5) {
      for(int var6 = this.vec3_arr2.length - 1; var6 >= 0; --var6) {
         this.vec3_arr2[var6] = new AEVector3D();
      }

      this.lengths = new int[6];
      this.vec3_arr1[0] = new AEVector3D(0, 0, -4096);
      this.vec3_arr1[1] = new AEVector3D(0, 0, 4096);
      this.vec3_arr1[2] = new AEVector3D();
      this.vec3_arr1[3] = new AEVector3D();
      this.vec3_arr1[4] = new AEVector3D();
      this.vec3_arr1[5] = new AEVector3D();
      this.screenWidth = var1;
      this.screenHeight = var2;
      this.sub_5c(var3, var4, var5);
   }

   public final void sub_109(boolean var1) {
      if (this.var_c1 || var1) {
         if (this.meshGroup != null) {
            this.tempTransform = this.meshGroup.tempTransform.sub_6a(this.currentTransform, this.tempTransform);
         } else {
            this.tempTransform.set(this.currentTransform);
         }

         this.vec3_arr2 = this.tempTransform.sub_ace(this.vec3_arr1, this.vec3_arr2);
         vec1 = this.tempTransform.getPosition(vec1);
         vec3_2 = this.tempTransform.getDirection(vec3_2);
         vec3_3.set(vec3_2);
         vec3_2.scale(-this.var_a1);
         vec3_3.scale(-this.var_c5);
         vec3_2.add(vec1);
         vec3_3.add(vec1);
         this.lengths[0] = vec3_2.squaredLength(this.vec3_arr2[0]);
         this.lengths[1] = vec3_3.squaredLength(this.vec3_arr2[1]);
         this.lengths[2] = vec1.squaredLength(this.vec3_arr2[2]);
         this.lengths[3] = vec1.squaredLength(this.vec3_arr2[3]);
         this.lengths[4] = vec1.squaredLength(this.vec3_arr2[4]);
         this.lengths[5] = vec1.squaredLength(this.vec3_arr2[5]);
         this.var_c1 = false;
         this.var_ee = false;
      }

   }

   public void sub_5c(int var1, int var2, int var3) {
      this.var_a1 = var2;
      this.var_c5 = var3;
      var2 = AEMath.sin(var1 >> 1);
      var1 = AEMath.cos(var1 >> 1);
      this.vec3_arr1[2].set(var1, 0, -var2);
      this.vec3_arr1[3].set(-var1, 0, -var2);
      this.vec3_arr1[4].set(0, -var1, -var2);
      this.vec3_arr1[5].set(0, var1, -var2);
      this.var_23d = (var2 << 12) / var1;
      this.var_25c = this.var_23d * ((this.screenWidth << 12) / this.screenHeight) >> 12;
   }

   public final void sub_ba(int var1) {
      this.sub_5c(var1, this.var_a1, this.var_c5);
   }

   public final boolean sub_fa(AEVector3D var1) {
      if ((var1 = this.tempTransform.sub_b11(var1)).z > this.var_a1) {
         return false;
      } else {
         int var2 = this.var_25c * var1.z >> 12;
         int var3 = this.var_23d * var1.z >> 12;
         if (var2 != 0 && var3 != 0) {
            var1.x = -((var1.x << 11) / var2 * this.screenWidth >> 12) + (this.screenWidth >> 1);
            var1.y = ((var1.y << 11) / var3 * this.screenHeight >> 12) + (this.screenHeight >> 1);
            return var1.x >= 0 && var1.y >= 0 && var1.x < this.screenWidth && var1.y < this.screenHeight;
         } else {
            return false;
         }
      }
   }

   public final byte sub_14a(AEBoundingSphere var1) {
      for(int var3 = 5; var3 >= 0; --var3) {
         int var2;
         if ((var2 = var1.center.squaredLength(this.vec3_arr2[var3]) - this.lengths[var3]) < -var1.radius) {
            return 0;
         }

         if (AEMath.abs(var2) < var1.radius) {
            return 1;
         }
      }

      return 2;
   }

   public final void appendToRender(Camera var1, Class_db var2) {
   }

   public final void forceAppendToRender(Camera var1, Class_db var2) {
   }

   public abstract void sub_19c();

   public static Camera sub_1b1(int var0, int var1, int var2, int var3, int var4) {
      return new JSRCamera(var0, var1, var2, var3, var4);
   }
}
