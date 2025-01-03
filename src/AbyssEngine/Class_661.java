package AbyssEngine;

public final class Class_661 {
   private Class_e19[] var_32;
   private boolean var_91 = false;
   private int var_f7 = 0;

   public Class_661(int[] var1) {
      this.var_32 = new Class_e19[var1.length / 3];

      for(int var2 = 0; var2 < var1.length; var2 += 3) {
         this.var_32[var2 / 3] = new Class_e19(var1[var2], var1[var2 + 1], var1[var2 + 2], this);
      }

   }

   public final void sub_62() {
      this.var_32 = null;
   }

   public final void sub_9f(AEVector3D var1) {
      this.var_32[0].var_c7 = var1.x;
      this.var_32[0].var_282 = var1.y;
      this.var_32[0].var_2f9 = var1.z;
   }

   public final void sub_ad() {
      for(int var1 = 0; var1 < this.var_32.length; ++var1) {
         this.var_32[var1].sub_af();
      }

      this.var_f7 = 0;
   }

   public final void sub_e1(boolean var1) {
      this.var_91 = var1;
   }

   public final Class_e19 sub_11e(int var1) {
      return var1 < this.var_32.length ? this.var_32[var1] : null;
   }

   public final Class_e19 sub_176() {
      return this.var_f7 < this.var_32.length ? this.var_32[this.var_f7] : null;
   }

   public final Class_e19 sub_1d7() {
      return this.var_32[this.var_32.length - 1];
   }

   public final int sub_229() {
      return this.var_f7;
   }

   public final void sub_285(int var1) {
      if (this.var_f7 < this.var_32.length - 1) {
         this.var_f7 = 1;
      }

      this.var_32[0].sub_34(false);
      this.var_32[0].var_35b = true;
   }

   public final int sub_2c6() {
      return this.var_32.length;
   }

   public final void sub_31e(int var1, int var2, int var3) {
      if (this.var_f7 < this.var_32.length && var1 - this.var_32[this.var_f7].var_c7 < 2000 && var1 - this.var_32[this.var_f7].var_c7 > -2000 && var2 - this.var_32[this.var_f7].var_282 < 2000 && var2 - this.var_32[this.var_f7].var_282 > -2000 && var3 - this.var_32[this.var_f7].var_2f9 < 2000 && var3 - this.var_32[this.var_f7].var_2f9 > -2000) {
         this.var_32[this.var_f7].sub_34(false);
         this.var_32[this.var_f7].var_35b = true;
         ++this.var_f7;
         if (this.var_91 && this.var_f7 > this.var_32.length - 1) {
            this.var_f7 = 0;

            for(var1 = 0; var1 < this.var_32.length; ++var1) {
               this.var_32[var1].sub_af();
            }
         }

         if (this.var_f7 < this.var_32.length) {
            this.var_32[this.var_f7].sub_34(true);
         }
      }

   }

   public final boolean sub_349() {
      return this.var_91;
   }

   public final Class_661 sub_38e() {
      Class_661 var1 = this.sub_3ad();

      for(int var2 = 0; var2 < var1.var_32.length; ++var2) {
         if (this.var_32[var2].var_35b) {
            var1.var_32[var2].var_35b = true;
         }
      }

      var1.var_f7 = this.var_f7;
      return var1;
   }

   public final Class_661 sub_3ad() {
      int[] var1 = new int[this.var_32.length * 3];

      for(int var2 = 0; var2 < this.var_32.length; ++var2) {
         var1[var2 * 3] = this.var_32[var2].var_c7;
         var1[var2 * 3 + 1] = this.var_32[var2].var_282;
         var1[var2 * 3 + 2] = this.var_32[var2].var_2f9;
      }

      Class_661 var4;
      Class_661 var10000 = var4 = new Class_661(var1);
      boolean var3 = this.var_91;
      var10000.var_91 = var3;
      return var4;
   }
}
