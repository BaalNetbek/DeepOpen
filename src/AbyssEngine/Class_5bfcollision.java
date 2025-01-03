package AbyssEngine;

public final class Class_5bfcollision extends AbstractBounding {
   private int var_131;
   private int var_15a;
   private int var_245;
   private int var_291;

   public Class_5bfcollision(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, 0, 0, 0);
      this.var_131 = var7;
      this.sub_e2(var1, var2, var3);
   }

   public final boolean sub_4f(int var1, int var2, int var3) {
      return this.sub_a0(var1, var2, var3) ? true : super.sub_4f(var1, var2, var3);
   }

   public final boolean sub_a0(int var1, int var2, int var3) {
      return var1 - this.var_15a < this.var_131 && var1 - this.var_15a > -this.var_131 && var2 - this.var_245 < this.var_131 && var2 - this.var_245 > -this.var_131 && var3 - this.var_291 < this.var_131 && var3 - this.var_291 > -this.var_131;
   }

   public final void sub_e2(int var1, int var2, int var3) {
      super.sub_e2(var1, var2, var3);
      this.var_15a = var1 + this.offsetX;
      this.var_245 = var2 + this.offsetY;
      this.var_291 = var3 + this.offsetZ;
   }
}
