package AbyssEngine;

public final class Explosion {
   private int[] var_158;
   private int[] var_1af;
   private int animationPlayTime;
   private AbstractMesh[] explosions;
   private AbstractMesh var_366;

   public Explosion(int var1) {
      if (var1 > 1) {
         this.explosions = new AbstractMesh[var1];
         this.var_158 = new int[var1];

         for(var1 = 0; var1 < this.explosions.length; ++var1) {
            this.explosions[var1] = AEResourceManager.getGeometryResource(9992);
            this.explosions[var1].sub_918(100);
            this.explosions[var1].sub_980(1, 20);
            this.explosions[var1].sub_7af(8192, 8192, 8192);
            this.explosions[var1].setRenderLayer(2);
            this.explosions[var1].sub_a04();
            if (var1 == 0) {
               this.var_158[var1] = 0;
            } else {
               this.var_158[var1] = this.var_158[var1 - 1] + GameStatus.random.nextInt(1000) + 1000;
            }
         }
      }

      this.var_366 = AEResourceManager.getGeometryResource(9992);
      this.var_366.sub_918(100);
      this.var_366.sub_980(1, 20);
      this.var_366.sub_7af(8192, 8192, 8192);
      this.var_366.setRenderLayer(2);
      this.var_366.sub_a04();
   }

   public final void sub_36() {
      this.animationPlayTime = 0;
   }

   public final void sub_b1() {
      if (this.var_366 != null) {
         this.var_366.sub_7af(32768, 32768, 32768);
         this.var_366.sub_918(200);
      }

   }

   public final void sub_fa() {
      this.var_158 = null;
      this.var_1af = null;
      if (this.explosions != null) {
         for(int var1 = 0; var1 < this.explosions.length; ++var1) {
            if (this.explosions[var1] != null) {
               this.explosions[var1].destroy();
            }
         }
      }

      this.explosions = null;
   }

   public final void sub_141(int var1, int var2, int var3) {
      if (this.explosions == null) {
         this.var_366.moveTo(var1, var2, var3);
         this.var_366.sub_9aa((byte)1);
         this.animationPlayTime = 0;
      } else {
         for(int var4 = 0; var4 < this.explosions.length; ++var4) {
            this.explosions[var4].moveTo(var1, var2, var3);
         }

         this.var_366.moveTo(var1, var2, var3);
      }
   }

   public final boolean sub_154() {
      return this.var_366 != null && this.var_366.sub_a37();
   }

   public final void sub_179(long var1) {
      this.animationPlayTime = (int)((long)this.animationPlayTime + var1);
      if (this.explosions != null) {
         for(int var3 = 0; var3 < this.explosions.length; ++var3) {
            if (this.animationPlayTime > this.var_158[var3]) {
               if (var3 == this.explosions.length - 1) {
                  this.var_366.sub_7af(16384, 16384, 16384);
                  this.var_366.sub_9aa((byte)1);
               }

               this.explosions[var3].sub_9aa((byte)1);
            }
         }

      } else {
         GameStatus.renderer.sub_87(this.var_366);
      }
   }

   public final boolean sub_185() {
      if (this.explosions != null) {
         return this.animationPlayTime > this.var_158[this.var_158.length - 1] + 1000;
      } else {
         return this.animationPlayTime > 2000;
      }
   }
}
