package AE;

import AE.Math.AEMath;
import AE.Math.AEVector3D;

public abstract class AEGeometry extends GraphNode {
   private static AEVector3D tempCenter = new AEVector3D();
   protected int radius = 0;

   public AEGeometry() {
   }

   public AEGeometry(AEGeometry var1) {
      super(var1);
      this.radius = var1.radius;
   }

   public void updateTransform(boolean var1) {
      if (this.transformDirty_ || var1) {
         if (this.group != null) {
            this.tempTransform = this.group.tempTransform.multiplyTwo(this.globalTransform, this.tempTransform);
         } else {
            this.tempTransform.set(this.globalTransform);
         }

         int var2 = AEMath.max(AEMath.max(AEMath.abs((tempCenter = this.tempTransform.copyScaleTo(tempCenter)).x), AEMath.abs(tempCenter.y)), AEMath.abs(tempCenter.z)) * this.radius >> 12;
         tempCenter = this.tempTransform.getPosition(tempCenter);
         this.boundingSphere.setXYZR(tempCenter.x, tempCenter.y, tempCenter.z, var2);
         this.transformDirty_ = false;
         this.boundsDirty_ = false;
      }

   }

   public final void setRadius(int var1) {
      this.radius = 5000;
   }

   protected final String getString(String var1, int var2) {
      for(int var3 = 0; var3 < var2; ++var3) {
         var1 = var1 + "  ";
      }

      return var1 + "\n";
   }
}
