package AbyssEngine;

public final class Class_fd6 implements Class_121c {
   private GraphNode var_8b;
   private GraphNode var_19a;
   private int var_1fb;
   private GraphNode var_26e;
   private static AEVector3D var_28c;
   private static AEVector3D var_31b;
   private static AEVector3D var_342;
   private AEVector3D var_3a2;
   private boolean var_3c9;

   public Class_fd6(GraphNode var1, GraphNode var2) {
      this(var1, var2, (AEVector3D)null, 0);
   }

   private Class_fd6(GraphNode var1, GraphNode var2, AEVector3D var3, int var4) {
      this.var_8b = var1;
      this.var_26e = var2;
      this.var_1fb = 0;
      this.var_3c9 = true;
      this.var_3a2 = null;
      if (var_28c == null) {
         var_28c = new AEVector3D();
      }

      if (var_31b == null) {
         var_31b = new AEVector3D();
      }

      if (var_342 == null) {
         var_342 = new AEVector3D();
      }

      for(this.var_19a = var1; this.var_19a.sub_a4() != null; this.var_19a = this.var_19a.sub_a4()) {
      }

      Object var5;
      for(var5 = var2; ((GraphNode)var5).sub_a4() != null; var5 = ((GraphNode)var5).sub_a4()) {
      }

      if (var5 == this.var_19a) {
         this.var_19a = null;
      }

   }

   public final void sub_36(GraphNode var1) {
      this.var_8b = var1;
   }

   public final void sub_68(GraphNode var1) {
      this.var_26e = var1;
   }

   public final void sub_bf(AEVector3D var1, int var2) {
      this.var_3a2 = var1;
      this.var_1fb = var2;
      if (var1 != null) {
         this.var_3a2.normalize();
      }

   }

   public final void sub_2aa() {
      if (this.var_3c9) {
         if (this.var_19a != null) {
            this.var_19a.sub_109(false);
         }

         var_28c = this.var_8b.sub_22a(var_28c);
         var_28c = this.var_26e.sub_a4().sub_8a0().sub_b11(var_28c);
         (var_31b = this.var_26e.sub_216(var_31b)).subtract(var_28c);
         var_31b.normalize();
         if (this.var_3a2 != null) {
            var_342.set(this.var_3a2);
         } else {
            var_342.set(0, 4096, 0);
         }

         (var_28c = var_342.crossProduct(var_31b, var_28c)).normalize();
         if (AEMath.abs(var_28c.x + var_28c.y + var_28c.z) < 4) {
            var_342.set(4096, 0, 0);
            (var_28c = var_342.crossProduct(var_31b, var_28c)).normalize();
         }

         switch(this.var_1fb) {
         case 0:
         case 2:
            var_342 = var_31b.crossProduct(var_28c, var_342);
            break;
         case 1:
            var_31b = var_28c.crossProduct(var_342, var_31b);
         }

         this.var_26e.sub_90e(var_28c, var_342, var_31b);
      }

   }

   public final boolean sub_356() {
      return this.var_3c9;
   }

   public final void sub_120(boolean var1) {
      this.var_3c9 = var1;
   }
}
