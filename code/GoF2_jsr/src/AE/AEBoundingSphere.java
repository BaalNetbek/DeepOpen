package AE;

import AE.Math.AEMath;
import AE.Math.AEVector3D;

public final class AEBoundingSphere {
   public AEVector3D center;
   public int radius;

   private AEBoundingSphere(int var1, int var2, int var3, int var4) {
      this.center = new AEVector3D(0, 0, 0);
      this.radius = 0;
   }
   
   public AEBoundingSphere() {
      this(0, 0, 0, 0);
   }

   public AEBoundingSphere(AEBoundingSphere var1) {
      this.center = new AEVector3D(var1.center.x, var1.center.y, var1.center.z);
      this.radius = var1.radius;
   }

   public final void merge(AEBoundingSphere var1) {
      if (this.radius == 0) {
         this.set(var1);
      } else if (var1.radius == 0) {
         this.set(this); // can just return
      } else {
         int var4 = var1.center.x - this.center.x;
         int var5 = var1.center.y - this.center.y;
         int var6 = var1.center.z - this.center.z;
         int var7;
         int var8 = (var7 = var1.radius - this.radius) * var7;
         int var9 = var4 * var4 + var5 * var5 + var6 * var6;
         if (var8 >= var9) {
            if (var7 >= 0) {
               this.set(var1.center, var1.radius);
            } else {
               this.set(this.center, this.radius);
            }
         } else {
            var8 = AEMath.sqrt((long)var9 << 12);
            var7 = (int)(((long)((var7 << 12) + var8) << 12) / (long)(var8 * 2));
            this.setXYZR(
            		this.center.x + (int)((long)var7 * ((long)var4 << 12) >> 24),
            		this.center.y + (int)((long)var7 * ((long)var5 << 12) >> 24),
            		this.center.z + (int)((long)var7 * ((long)var6 << 12) >> 24),
            		(var8 >> 12) + var1.radius + this.radius >> 1
            		);
         }
      }

   }

   private void set(AEBoundingSphere var1) {
      this.center.set(var1.center);
      this.radius = var1.radius;
   }

   public final void setXYZR(int var1, int var2, int var3, int var4) {
      this.center.x = var1;
      this.center.y = var2;
      this.center.z = var3;
      this.radius = var4;
   }

   private void set(AEVector3D var1, int var2) {
      this.center.set(var1);
      this.radius = var2;
   }

   public final String toString() {
      return "AEBoundingSphere | x: " + this.center.x 
    		  + " y: " + this.center.y 
    		  + " z: " + this.center.z 
    		  + " r: " + this.radius;
   }
}
