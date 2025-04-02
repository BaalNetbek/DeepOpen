package AbyssEngine;

public final class Class_a6e {
   private AEVector3D var_90 = new AEVector3D();
   private AEVector3D var_d9 = new AEVector3D();
   private AEVector3D var_150 = new AEVector3D();
   private AEVector3D var_1ce = new AEVector3D();
   private KIPlayer var_242 = null;
   private static int[][] var_2a4 = new int[][]{{16, 13, 47, 19}, {16, 13, 47, 19}, {16, 13, 47, 19}};
   private int var_2c1 = 100;
   private int[] var_2d7;
   private int[] var_318;
   private int[] var_322;
   private boolean var_384 = false;
   private AbstractMesh var_424 = AbstractMesh.sub_976unk(0, 10, (byte)2);

   public Class_a6e(Group var1, int var2) {
      this.var_424.setTexture(AEResourceManager.sub_10b(1));
      this.var_424.setRenderLayer(2);
      this.var_318 = new int[120];
      this.var_2d7 = new int[120];
      this.var_322 = new int[80];

      for(int var3 = 0; var3 < this.var_322.length; var3 += 8) {
         this.var_322[var3] = var_2a4[var2][0];
         this.var_322[var3 + 1] = var_2a4[var2][1];
         this.var_322[var3 + 2] = var_2a4[var2][2];
         this.var_322[var3 + 3] = var_2a4[var2][1];
         this.var_322[var3 + 4] = var_2a4[var2][2];
         this.var_322[var3 + 5] = var_2a4[var2][3];
         this.var_322[var3 + 6] = var_2a4[var2][0];
         this.var_322[var3 + 7] = var_2a4[var2][3];
      }

   }

   public final void sub_18() {
      if (this.var_384) {
         GameStatus.var_8ce.sub_87(this.var_424);
      }

   }

   public final void sub_43(long var1, MGameContext var3, Level var4, Hud var5) {
      KIPlayer var6 = var3.var_fdf;
      if (this.var_242 != null || var6 != null) {
         if (this.var_242 == null) {
            if (!var6.sub_6ef()) {
               return;
            }

            if (var6.waste == null) {
               var6.setWasteMesh(0);
            }

            this.var_90 = var6.waste.sub_216(this.var_90);
            this.var_90.subtract(var4.getPlayerEgo().sub_8c1());
            if (var6.isOutOfScope() || var6.isCrate()) {
               var6.sub_34(false);
            }

            this.var_242 = var6;
            this.var_384 = true;
            return;
         }

         if (this.var_242.waste == null) {
            var3.var_fdf = null;
            var3.contextShip = null;
            return;
         }

         this.var_d9 = this.var_242.waste.sub_22a(this.var_d9);
         this.var_150 = var4.getPlayerEgo().var_50e.sub_22a(this.var_150);
         this.var_90 = this.var_d9.subtract(this.var_150, this.var_90);
         this.var_1ce.set(this.var_90);
         this.var_d9 = var4.getPlayerEgo().var_50e.sub_36a(this.var_d9);
         this.var_d9.scale(1024);
         this.var_150.add(this.var_d9);
         AEVector3D var10000 = this.var_1ce;
         var10000.x /= 10;
         var10000 = this.var_1ce;
         var10000.y /= 10;
         var10000 = this.var_1ce;
         var10000.z /= 10;

         int var7;
         int var8;
         for(var7 = 0; var7 < this.var_318.length >> 1; var7 += 12) {
            var8 = var7 / 12;
            this.var_318[var7] = this.var_150.x + var8 * this.var_1ce.x + this.var_2c1;
            this.var_318[var7 + 1] = this.var_150.y + var8 * this.var_1ce.y;
            this.var_318[var7 + 2] = this.var_150.z + var8 * this.var_1ce.z;
            this.var_318[var7 + 3] = this.var_150.x + var8 * this.var_1ce.x - this.var_2c1;
            this.var_318[var7 + 4] = this.var_150.y + var8 * this.var_1ce.y;
            this.var_318[var7 + 5] = this.var_150.z + var8 * this.var_1ce.z;
            this.var_318[var7 + 6] = this.var_150.x + (var8 + 1) * this.var_1ce.x - this.var_2c1;
            this.var_318[var7 + 7] = this.var_150.y + (var8 + 1) * this.var_1ce.y;
            this.var_318[var7 + 8] = this.var_150.z + (var8 + 1) * this.var_1ce.z;
            this.var_318[var7 + 9] = this.var_150.x + (var8 + 1) * this.var_1ce.x + this.var_2c1;
            this.var_318[var7 + 10] = this.var_150.y + (var8 + 1) * this.var_1ce.y;
            this.var_318[var7 + 11] = this.var_150.z + (var8 + 1) * this.var_1ce.z;
         }

         for(var7 = this.var_318.length >> 1; var7 < this.var_318.length; var7 += 12) {
            var8 = (var7 - (this.var_318.length >> 1)) / 12;
            this.var_318[var7] = this.var_150.x + var8 * this.var_1ce.x;
            this.var_318[var7 + 1] = this.var_150.y + var8 * this.var_1ce.y + this.var_2c1;
            this.var_318[var7 + 2] = this.var_150.z + var8 * this.var_1ce.z + this.var_2c1;
            this.var_318[var7 + 3] = this.var_150.x + var8 * this.var_1ce.x;
            this.var_318[var7 + 4] = this.var_150.y + var8 * this.var_1ce.y - this.var_2c1;
            this.var_318[var7 + 5] = this.var_150.z + var8 * this.var_1ce.z - this.var_2c1;
            this.var_318[var7 + 6] = this.var_150.x + (var8 + 1) * this.var_1ce.x;
            this.var_318[var7 + 7] = this.var_150.y + (var8 + 1) * this.var_1ce.y - this.var_2c1;
            this.var_318[var7 + 8] = this.var_150.z + (var8 + 1) * this.var_1ce.z - this.var_2c1;
            this.var_318[var7 + 9] = this.var_150.x + (var8 + 1) * this.var_1ce.x;
            this.var_318[var7 + 10] = this.var_150.y + (var8 + 1) * this.var_1ce.y + this.var_2c1;
            this.var_318[var7 + 11] = this.var_150.z + (var8 + 1) * this.var_1ce.z + this.var_2c1;
         }

         for(var7 = 0; var7 < this.var_318.length; var7 += 3) {
            this.var_2d7[var7] = this.var_318[var7] - this.var_150.x;
            this.var_2d7[var7 + 1] = this.var_318[var7 + 1] - this.var_150.y;
            this.var_2d7[var7 + 2] = this.var_318[var7 + 2] - this.var_150.z;
         }

         this.var_424.moveTo(this.var_150.x, this.var_150.y, this.var_150.z);
         ((Class_4cMesh)this.var_424).sub_176(this.var_2d7, this.var_322);
         if (this.var_90.getLength() <= 400) {
            this.var_242.sub_2cb(var5);
            this.var_384 = false;
            this.var_242 = null;
            var3.var_fdf = null;
            var3.contextShip = null;
            return;
         }

         var3.var_fdf = null;
         this.var_90.normalize();
         this.var_90.scale((int)var1 * 10);
         this.var_242.waste.sub_18f(-this.var_90.x, -this.var_90.y, -this.var_90.z);
      }

   }
}
