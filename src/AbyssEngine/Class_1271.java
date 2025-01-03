package AbyssEngine;

public final class Class_1271 {
   private static int[] var_151 = new int[]{80, 255, 95, 254, 32, 240, 63, 239};
   private int var_1f3 = 60;
   private int[] var_2dd;
   private int[] var_323;
   private int[] var_3f9;
   private AbstractMesh var_54d;
   private AEVector3D var_592 = new AEVector3D();
   private boolean var_5dd = false;

   public Class_1271(int var1) {
      int var2 = var1 == 0 ? 16 : 13;
      this.var_54d = AbstractMesh.sub_976unk(0, var2, (byte)2);
      this.var_54d.setTexture(AEResourceManager.sub_10b(1));
      this.var_54d.setFlag_(2);
      this.var_323 = new int[var2 * 12];
      this.var_2dd = new int[var2 * 12];
      int var3 = var1 << 2;
      int var4 = var_151[var3 + 1];
      int var5 = var_151[var3 + 3];
      this.var_3f9 = new int[var2 * 8];

      for(var2 = 0; var2 < this.var_3f9.length; var2 += 8) {
         this.var_3f9[var2] = var_151[var3];
         this.var_3f9[var2 + 1] = var4;
         this.var_3f9[var2 + 2] = var_151[var3 + 2];
         this.var_3f9[var2 + 3] = var4;
         this.var_3f9[var2 + 4] = var_151[var3 + 2];
         this.var_3f9[var2 + 5] = var5;
         this.var_3f9[var2 + 6] = var_151[var3];
         this.var_3f9[var2 + 7] = var5;
         if (var1 == 1) {
            --var4;
            --var5;
         } else {
            var4 -= 2;
            var5 -= 2;
         }
      }

   }

   public final void sub_60() {
      this.var_54d.destroy();
      this.var_54d = null;
   }

   public final void sub_000(int var1) {
      this.var_1f3 = var1;
   }

   public final void sub_a6(AEVector3D var1) {
      if (this.var_5dd) {
         this.var_592.set(var1);
         this.var_5dd = false;
      }

      int var8 = this.var_592.z;
      int var7 = this.var_592.y;
      int var6 = this.var_592.x;
      int var5 = var1.z;
      int var4 = var1.y;
      int var3 = var1.x;
      Class_1271 var2 = this;
      this.var_323[0] = var3 - this.var_1f3;
      this.var_323[1] = var4;
      this.var_323[2] = var5;
      this.var_323[3] = var3 + this.var_1f3;
      this.var_323[4] = var4;
      this.var_323[5] = var5;
      this.var_323[6] = var6 + this.var_1f3;
      this.var_323[7] = var7;
      this.var_323[8] = var8;
      this.var_323[9] = var6 - this.var_1f3;
      this.var_323[10] = var7;
      this.var_323[11] = var8;

      for(var6 = this.var_323.length - 1; var6 >= 23; var6 -= 12) {
         var2.var_323[var6] = var2.var_323[var6 - 12];
         var2.var_323[var6 - 1] = var2.var_323[var6 - 13];
         var2.var_323[var6 - 2] = var2.var_323[var6 - 14];
         var2.var_323[var6 - 3] = var2.var_323[var6 - 15];
         var2.var_323[var6 - 4] = var2.var_323[var6 - 16];
         var2.var_323[var6 - 5] = var2.var_323[var6 - 17];
         var2.var_323[var6 - 6] = var2.var_323[var6 - 18];
         var2.var_323[var6 - 7] = var2.var_323[var6 - 19];
         var2.var_323[var6 - 8] = var2.var_323[var6 - 20];
         var2.var_323[var6 - 9] = var2.var_323[var6 - 21];
         var2.var_323[var6 - 10] = var2.var_323[var6 - 22];
         var2.var_323[var6 - 11] = var2.var_323[var6 - 23];
      }

      for(var6 = 0; var6 < var2.var_323.length; var6 += 3) {
         var2.var_2dd[var6] = var2.var_323[var6] - var3;
         var2.var_2dd[var6 + 1] = var2.var_323[var6 + 1] - var4;
         var2.var_2dd[var6 + 2] = var2.var_323[var6 + 2] - var5;
      }

      var2.var_54d.moveTo(var3, var4, var5);
      ((Class_4cMesh)var2.var_54d).sub_176(var2.var_2dd, var2.var_3f9);
      this.var_592.set(var1);
   }

   public final void sub_10a(AEVector3D var1) {
      for(int var2 = 0; var2 < this.var_323.length; var2 += 3) {
         this.var_323[var2] = var1.x;
         this.var_323[var2 + 1] = var1.y;
         this.var_323[var2 + 2] = var1.z;
      }

      this.var_592.set(var1);
      this.var_5dd = true;
   }

   public final void sub_160() {
      GameStatus.var_8ce.sub_87(this.var_54d);
   }
}
