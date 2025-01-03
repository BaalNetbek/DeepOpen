package AbyssEngine;

public final class LaserGun extends AbstractMesh implements Class_786 {
   private Gun var_95;
   private int var_18a = 80;
   private AbstractMesh var_1d0;
   private AbstractMesh var_1e8;
   private int[] var_2e2;
   private int[] var_375;
   private int[] var_3da;
   private static AEVector3D var_457;
   private static AEVector3D var_4a0;
   private static AEVector3D var_4ef;
   private AEVector3D var_56b;
   private static int[][] var_5e5 = new int[][]{{16, 13, 47, 19}, {32, 235, 65, 242}, {17, 54, 30, 58}};
   private static int[] var_617 = new int[]{0, 1, 9};
   private Level var_658;
   private boolean var_695;

   public LaserGun(Gun var1, int var2, Level var3) {
      this.var_658 = var3;
      this.var_95 = var1;
      var_457 = new AEVector3D();
      var_4a0 = new AEVector3D();
      var_4ef = new AEVector3D();
      this.var_56b = new AEVector3D();
      var2 -= 9;
      this.var_1e8 = AEResourceManager.getGeometryResource(6781);
      this.var_1e8.setFlag_(2);
      this.var_1e8.sub_980(var_617[var2], var_617[var2]);
      this.var_1e8.sub_9aa((byte)1);
      this.var_1e8.sub_7af(600, 600, 600);
      this.var_1d0 = AbstractMesh.sub_976unk(0, 10, (byte)2);
      this.var_1d0.setTexture(AEResourceManager.sub_10b(1));
      this.var_1d0.setFlag_(2);
      this.var_375 = new int[120];
      this.var_2e2 = new int[120];
      this.var_3da = new int[80];

      for(int var4 = 0; var4 < this.var_3da.length; var4 += 8) {
         this.var_3da[var4] = var_5e5[var2][0];
         this.var_3da[var4 + 1] = var_5e5[var2][1];
         this.var_3da[var4 + 2] = var_5e5[var2][2];
         this.var_3da[var4 + 3] = var_5e5[var2][1];
         this.var_3da[var4 + 4] = var_5e5[var2][2];
         this.var_3da[var4 + 5] = var_5e5[var2][3];
         this.var_3da[var4 + 6] = var_5e5[var2][0];
         this.var_3da[var4 + 7] = var_5e5[var2][3];
      }

      this.var_695 = false;
   }

   public final void destroy() {
      this.var_95.sub_183();
      this.var_95 = null;
      if (this.var_1d0 != null) {
         this.var_1d0.destroy();
      }

      this.var_1d0 = null;
   }

   public final void render() {
      if (!this.var_95.inAir) {
         this.var_695 = false;
      } else {
         if (!this.var_695 && this.var_95.inAir) {
            var_4a0.set(Crosshair.worldPos);
            this.var_695 = true;
         }

         Matrix var1 = this.var_658.getPlayerEgo().var_50e.sub_85f();
         this.var_56b.set(var1.getPosition(var_457));
         if (this.var_95.var_3de != null) {
            var_457.set(this.var_95.var_3de);
            var_4ef = var1.sub_a67(var_457, var_4ef);
            this.var_56b.add(var_4ef);
         }

         this.var_1e8.setRotation(var1.sub_752(), var1.sub_7a8(), var1.sub_7f1());
         this.var_1e8.sub_1f3(this.var_56b);
         var_457.set(this.var_56b);
         AEVector3D var10000 = var_4ef = var_4a0.subtract(var_457, var_4ef);
         var10000.x /= 10;
         var10000 = var_4ef;
         var10000.y /= 10;
         var10000 = var_4ef;
         var10000.z /= 10;

         int var2;
         int var3;
         for(var3 = 0; var3 < this.var_375.length >> 1; var3 += 12) {
            var2 = var3 / 12;
            this.var_375[var3] = var_457.x + var2 * var_4ef.x + this.var_18a;
            this.var_375[var3 + 1] = var_457.y + var2 * var_4ef.y;
            this.var_375[var3 + 2] = var_457.z + var2 * var_4ef.z;
            this.var_375[var3 + 3] = var_457.x + var2 * var_4ef.x - this.var_18a;
            this.var_375[var3 + 4] = var_457.y + var2 * var_4ef.y;
            this.var_375[var3 + 5] = var_457.z + var2 * var_4ef.z;
            this.var_375[var3 + 6] = var_457.x + (var2 + 1) * var_4ef.x - this.var_18a;
            this.var_375[var3 + 7] = var_457.y + (var2 + 1) * var_4ef.y;
            this.var_375[var3 + 8] = var_457.z + (var2 + 1) * var_4ef.z;
            this.var_375[var3 + 9] = var_457.x + (var2 + 1) * var_4ef.x + this.var_18a;
            this.var_375[var3 + 10] = var_457.y + (var2 + 1) * var_4ef.y;
            this.var_375[var3 + 11] = var_457.z + (var2 + 1) * var_4ef.z;
         }

         for(var3 = this.var_375.length >> 1; var3 < this.var_375.length; var3 += 12) {
            var2 = (var3 - (this.var_375.length >> 1)) / 12;
            this.var_375[var3] = var_457.x + var2 * var_4ef.x;
            this.var_375[var3 + 1] = var_457.y + var2 * var_4ef.y + this.var_18a;
            this.var_375[var3 + 2] = var_457.z + var2 * var_4ef.z + this.var_18a;
            this.var_375[var3 + 3] = var_457.x + var2 * var_4ef.x;
            this.var_375[var3 + 4] = var_457.y + var2 * var_4ef.y - this.var_18a;
            this.var_375[var3 + 5] = var_457.z + var2 * var_4ef.z - this.var_18a;
            this.var_375[var3 + 6] = var_457.x + (var2 + 1) * var_4ef.x;
            this.var_375[var3 + 7] = var_457.y + (var2 + 1) * var_4ef.y - this.var_18a;
            this.var_375[var3 + 8] = var_457.z + (var2 + 1) * var_4ef.z - this.var_18a;
            this.var_375[var3 + 9] = var_457.x + (var2 + 1) * var_4ef.x;
            this.var_375[var3 + 10] = var_457.y + (var2 + 1) * var_4ef.y + this.var_18a;
            this.var_375[var3 + 11] = var_457.z + (var2 + 1) * var_4ef.z + this.var_18a;
         }

         for(var3 = 0; var3 < this.var_375.length; var3 += 3) {
            this.var_2e2[var3] = this.var_375[var3] - var_457.x;
            this.var_2e2[var3 + 1] = this.var_375[var3 + 1] - var_457.y;
            this.var_2e2[var3 + 2] = this.var_375[var3 + 2] - var_457.z;
         }

         this.var_1d0.moveTo(var_457.x, var_457.y, var_457.z);
         ((Class_4cMesh)this.var_1d0).sub_176(this.var_2e2, this.var_3da);
         GameStatus.var_8ce.sub_87(this.var_1e8);
         GameStatus.var_8ce.sub_87(this.var_1d0);
         if (this.var_95.projectilesTimeLeft[0] <= 0) {
            this.var_95.inAir = false;
         }

      }
   }

   public final void sub_181(long var1) {
      this.var_95.sub_318(var1);
      if (this.var_95.inAir) {
         this.var_18a -= (int)var1 >> 2;
         if (this.var_18a < 0) {
            this.var_18a = 0;
            this.var_95.inAir = false;
            return;
         }
      } else {
         this.var_18a = 150;
      }

   }

   public final GraphNode sub_2b() {
      return null;
   }

   public final void setTexture(AbstractTexture var1) {
   }
}
