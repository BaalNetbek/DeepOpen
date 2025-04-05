package AbyssEngine;

public final class RocketGun extends BlasterGun {
   private Class_1271 var_49 = new Class_1271(1);
   private static AEVector3D var_1d7;
   private static AEVector3D var_28d;
   private static AEVector3D var_29f = new AEVector3D();
   private static int var_376;
   private static int var_3b5;
   private static int var_3f1;
   private boolean var_4a9;
   private MGameContext var_511;

   public RocketGun(Gun var1, AbstractMesh var2, boolean var3) {
      super(var1, (AbstractMesh)null);
      this.var_49.sub_000(100);
      this.var_1c8 = var2;
      this.var_4a9 = var3;
      var_1d7 = new AEVector3D();
   }

   public final void destroy() {
      super.destroy();
      if (this.var_49 != null) {
         this.var_49.sub_60();
      }

      this.var_49 = null;
   }

   public final void render() {
      this.gun.sub_341();
      if (this.gun.inAir) {
         this.var_49.sub_160();
         this.var_1c8.sub_109(true);
         GameStatus.renderer.sub_87(this.var_1c8);
      }

   }

   public final void sub_30() {
      if (this.gun.inAir) {
         GameStatus.renderer.getCamera().sub_fa(this.var_1c8.sub_22a(var_1d7));
      }

   }

   public final void sub_8a(MGameContext var1) {
      this.var_511 = var1;
   }

   public final void sub_181(long var1) {
      this.gun.sub_318(var1);
      var_1d7.set(this.gun.projectilesPos[0]);
      if (this.gun.var_42d) {
         this.var_49.sub_10a(var_1d7);
         this.gun.var_42d = false;
      }

      this.var_1c8.sub_1f3(var_1d7);
      var_211.set(this.gun.projectilesDir[0]);
      var_211.normalize();
      this.var_1c8.sub_85f().setOrientation(var_211);
      if (this.gun.inAir) {
         if (this.var_4a9 && this.gun.projectilesTimeLeft[0] < this.gun.range - 1500) {
            int var2 = (int)var1;
            RocketGun var8 = this;
            Player[] var3;
            if ((var3 = this.gun.sub_2d2()) != null) {
               label74: {
                  if (this.var_511 == null) {
                     int var4 = -1;
                     int var5 = Integer.MAX_VALUE;

                     for(int var6 = 0; var6 < var3.length; ++var6) {
                        if (var3[var6].sub_ace() && !var3[var6].sub_ae3() && !var3[var6].isAsteroid()) {
                           var_28d = var8.gun.projectilesPos[0];
                           var_1d7 = var3[var6].sub_a3c(var_1d7);
                           var_376 = var_28d.x - var_1d7.x;
                           var_3b5 = var_28d.y - var_1d7.y;
                           var_3f1 = var_28d.z - var_1d7.z;
                           int var7;
                           if (var_376 < 15000 && var_376 > -15000 && var_3b5 < 15000 && var_3b5 > -15000 && var_3f1 < 15000 && var_3f1 > -15000 && (var7 = var_376 * var_376 + var_3b5 * var_3b5 + var_3f1 * var_3f1) < var5) {
                              var4 = var6;
                              var5 = var7;
                           }
                        }
                     }

                     if (var4 < 0) {
                        break label74;
                     }

                     var_1d7 = var3[var4].sub_a3c(var_1d7);
                  } else if (this.var_511.sub_1fa() != null) {
                     var_1d7 = this.var_511.sub_1fa().sub_a9(var_1d7);
                  }

                  var_211.x = var_1d7.x - var8.gun.projectilesPos[0].x;
                  var_211.y = var_1d7.y - var8.gun.projectilesPos[0].y;
                  var_211.z = var_1d7.z - var8.gun.projectilesPos[0].z;
                  var_29f.set(var8.gun.projectilesDir[0]);
                  var_211.subtract(var_29f);
                  var_211.scale(var2);
                  var8.gun.projectilesDir[0] = var_29f.add(var_211, var8.gun.projectilesDir[0]);
                  var8.gun.projectilesDir[0].normalize();
                  var8.gun.projectilesDir[0].scale((int)(var8.gun.projectileSpeed * (float)var2) << 12);
                  AEVector3D var10000 = var8.gun.projectilesDir[0];
                  var10000.x >>= 12;
                  var10000 = var8.gun.projectilesDir[0];
                  var10000.y >>= 12;
                  var10000 = var8.gun.projectilesDir[0];
                  var10000.z >>= 12;
               }
            }
         }

         var_1d7.set(this.gun.projectilesPos[0]);
         this.var_49.sub_a6(var_1d7);
         if (var_1d7.z == 50000) {
            this.gun.inAir = false;
         }
      }

   }
}
