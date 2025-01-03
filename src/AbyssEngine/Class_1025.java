package AbyssEngine;

public final class Class_1025 {
   private int var_85;
   private int var_18e;
   private int var_1b0;
   private int var_1fd;

   public Class_1025(int var1, int var2) {
      this.var_85 = var1;
      this.var_18e = var2 - var1;
      this.var_1b0 = 3072;
   }

   public final void sub_b7(int var1) {
      this.var_1b0 += var1;
      this.var_1b0 = this.var_1b0 > 5120 ? 5120 : this.var_1b0;
      this.var_1fd = (((AEMath.sin(this.var_1b0) >> 1) + 2048) * this.var_18e >> 12) + this.var_85;
   }

   public final void sub_fe(int var1, int var2) {
      this.var_85 = var1;
      this.var_18e = var2 - var1;
      this.var_1b0 = 3072;
   }

   public final int sub_135() {
      return this.var_1fd;
   }

   public final boolean sub_14d(boolean var1) {
      return this.var_1b0 == 5120;
   }
}
