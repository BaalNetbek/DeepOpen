package AbyssEngine;

import java.util.Random;

public final class Class_490 {
   public AbstractMesh var_38;
   private int[] var_ac;
   private int var_ff;
   private int var_161;
   private int var_180;

   public Class_490(int var1, AbstractTexture var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, byte var11) {
      this.var_161 = var8;
      this.var_ff = var9;
      this.var_180 = var10;
      this.var_ac = new int[var1];
      short var15 = 256;
      this.var_38 = new Class_14c5Mesh(var15, var4, var5, var6, var7, var8, var1, var11);
      this.var_38.setTexture(var2);
      Class_14c5Mesh var13;
      int[] var16 = (var13 = (Class_14c5Mesh)this.var_38).sub_46();
      int[] var14 = var13.updateSomeArray_();
      Random var17 = new Random();

      for(var5 = 0; var5 < var1; ++var5) {
         var16[var5 * 3] = var17.nextInt(var8 >> 1) - (var8 >> 2);
         var16[var5 * 3 + 1] = var17.nextInt(var8 >> 1) - (var8 >> 2);
         var16[var5 * 3 + 2] = var17.nextInt(var8 >> 1) - (var8 >> 2);
         var14[var5] = 0;
         this.var_ac[var5] = var17.nextInt(var10) - (var10 >> 1) + this.var_ff;
      }

   }

   public final void sub_4b() {
      Class_14c5Mesh var1;
      int[] var2 = (var1 = (Class_14c5Mesh)this.var_38).sub_46();
      int[] var3 = var1.updateSomeArray_();
      int[] var6 = var1.sub_53();
      Random var4 = new Random();

      for(int var5 = 0; var5 < var3.length; ++var5) {
         var2[var5 * 3] = var4.nextInt(this.var_161 >> 1) - (this.var_161 >> 2);
         var2[var5 * 3 + 1] = var4.nextInt(this.var_161 >> 1) - (this.var_161 >> 2);
         var2[var5 * 3 + 2] = var4.nextInt(this.var_161 >> 1) - (this.var_161 >> 2);
         var3[var5] = 0;
         var6[var5] = -1;
         this.var_ac[var5] = var4.nextInt(this.var_180) - (this.var_180 >> 1) + this.var_ff;
      }

   }

   public final void sub_a6(int var1) {
      int[] var2 = ((Class_14c5Mesh)this.var_38).updateSomeArray_();
      int[] var3 = ((Class_14c5Mesh)this.var_38).sub_53();
      int var4 = var1 * this.var_161 / this.var_ff;

      for(int var5 = 0; var5 < var2.length; ++var5) {
         if (this.var_ac[var5] > 0 && this.var_ac[var5] < this.var_ff) {
            var2[var5] += var4;
         } else if (this.var_ac[var5] > -1024) {
            int var6 = (var6 = (var3[var5] >>> 24) - (var1 >> 2)) < 0 ? 0 : var6;
            var3[var5] = var6 << 24 | 16777215;
         } else {
            var2[var5] = 0;
         }

         int[] var10000 = this.var_ac;
         var10000[var5] -= var1;
      }

   }
}
