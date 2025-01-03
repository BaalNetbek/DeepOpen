package AbyssEngine;

public final class Class_db {
   private Camera var_52 = null;
   private GraphNode var_7d;
   private MeshArray[] var_ca = new MeshArray[1];
   private boolean[] var_d6;
   private AbstractGraphics3D var_10d;

   public Class_db(AbstractGraphics3D var1) {
      this.var_ca[0] = new MeshArray();
      this.var_d6 = new boolean[1];
      this.var_d6[0] = true;
      this.var_10d = var1;
   }

   public final void sub_19(Camera var1) {
      this.var_52 = var1;
      this.var_52.sub_19c();

      for(this.var_7d = this.var_52; this.var_7d.sub_a4() != null; this.var_7d = this.var_7d.sub_a4()) {
      }

   }

   public final Camera getCamera() {
      return this.var_52;
   }

   public final void sub_87(GraphNode var1) {
      try {
         if (var1 != null && this.var_ca != null && this.var_52 != null) {
            var1.sub_109(false);
            if (this.var_7d != null) {
               this.var_7d.sub_109(false);
            } else {
               this.var_52.sub_109(false);
            }

            var1.sub_11b(this.var_52, this);
         }

      } catch (Exception var2) {
         var2.printStackTrace();
      }
   }

   public final void sub_95() {
      for(int var1 = 0; var1 < this.var_ca.length; ++var1) {
         this.var_ca[var1].render();
         if (this.var_d6[var1]) {
            this.var_10d.clear();
         }

         this.var_ca[var1].reset();
      }

   }

   public final void sub_cc(long var1) {
      for(int var3 = 0; var3 < this.var_ca.length; ++var3) {
         this.var_ca[var3].sub_4a(var1);
         this.var_ca[var3].render();
         if (this.var_d6[var3]) {
            this.var_10d.clear();
         }

         this.var_ca[var3].reset();
      }

   }

   public final void sub_120() {
      boolean var1 = true;
      MeshArray[] var2 = new MeshArray[this.var_ca.length + 1];
      System.arraycopy(this.var_ca, 0, var2, 0, this.var_ca.length);
      var2[this.var_ca.length] = new MeshArray();
      this.var_ca = var2;
      boolean[] var3 = new boolean[this.var_d6.length + 1];
      System.arraycopy(this.var_d6, 0, var3, 0, this.var_d6.length);
      var3[this.var_d6.length] = true;
      this.var_d6 = var3;
   }

   public final void sub_177(int var1, AbstractMesh var2) {
      this.var_ca[var1].append(var2);
   }
}
