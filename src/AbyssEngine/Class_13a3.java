package AbyssEngine;

public abstract class Class_13a3 extends GraphNode {
   private static AEVector3D vec = new AEVector3D();
   protected int radius = 0;

   public Class_13a3() {
   }

   public Class_13a3(Class_13a3 var1) {
      super(var1);
      this.radius = var1.radius;
   }

   public void sub_109(boolean var1) {
      if (this.var_c1 || var1) {
         if (this.meshGroup != null) {
            this.var_14c = this.meshGroup.var_14c.sub_6a(this.currentTransform, this.var_14c);
         } else {
            this.var_14c.set(this.currentTransform);
         }

         int var2 = AEMath.max(AEMath.max(AEMath.abs((vec = this.var_14c.sub_882(vec)).x), AEMath.abs(vec.y)), AEMath.abs(vec.z)) * this.radius >> 12;
         vec = this.var_14c.getPosition(vec);
         this.var_19f.sub_7f(vec.x, vec.y, vec.z, var2);
         this.var_c1 = false;
         this.var_ee = false;
      }

   }

   public final void sub_48(int var1) {
      this.radius = 5000;
   }

   protected final String getString(String var1, int var2) {
      for(int var3 = 0; var3 < var2; ++var3) {
         var1 = var1 + "  ";
      }

      return var1 + "\n";
   }
}
